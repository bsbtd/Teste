local api_matcher = require "api-umbrella.proxy.middleware.api_matcher"
local config = require "api-umbrella.proxy.models.file_config"
local error_handler = require "api-umbrella.proxy.error_handler"
local host_normalize = require "api-umbrella.utils.host_normalize"
local packed_shared_dict = require "api-umbrella.utils.packed_shared_dict"
local redirect_matches_to_https = require "api-umbrella.utils.redirect_matches_to_https"
local website_matcher = require "api-umbrella.proxy.middleware.website_matcher"

local get_packed = packed_shared_dict.get_packed
local ngx_var = ngx.var

-- Determine the protocol/scheme and port this connection represents, based on
-- forwarded info or override config.
local http_port = tostring(config["http_port"])
local https_port = tostring(config["https_port"])
local server_port = ngx_var.server_port
local scheme = ngx_var.scheme
local forwarded_protocol = ngx_var.http_x_forwarded_proto
local forwarded_port = ngx_var.http_x_forwarded_port
local real_proto
local real_port
if server_port == https_port then
  real_proto = config["override_public_https_proto"] or forwarded_protocol or scheme
  real_port = config["override_public_https_port"] or forwarded_port or https_port
elseif server_port == http_port then
  real_proto = config["override_public_http_proto"] or forwarded_protocol or scheme
  real_port = config["override_public_http_port"] or forwarded_port or http_port
else
  real_proto = forwarded_protocol or scheme
  real_port = forwarded_port or http_port
end

-- Determine the host, based on forwarded information.
local real_host
if config["router"]["match_x_forwarded_host"] then
  real_host = ngx_var.http_x_forwarded_host
end
if not real_host then
  real_host = ngx_var.http_host or ngx_var.host
end

-- Append the port to the host header.
--
-- When the port is overriden, always append it to the host header (replacing
-- any existing values). In other situations, only append the port if it's a
-- non-default port (not 80 or 443) and the host header doesn't already contain
-- a port.
if real_proto == "http" and config["override_public_http_port"] then
  real_host = ngx.re.sub(real_host, "(:.*$|$)", ":" .. config["override_public_http_port"], "jo")
elseif real_proto == "https" and config["override_public_https_port"] then
  real_host = ngx.re.sub(real_host, "(:.*$|$)", ":" .. config["override_public_https_port"], "jo")
elseif not ngx.re.find(real_host, ":", "jo") then
  if not (real_proto == "http" and real_port == "80") or not (real_proto == "https" and real_port == "443") then
    real_host = real_host .. ":" .. real_port
  end
end

-- Cache various "ngx.var" lookups that are repeated throughout the stack,
-- so they don't allocate duplicate memory during the request, and since
-- ngx.var lookups are apparently somewhat expensive.
ngx.ctx.args = ngx_var.args
ngx.ctx.arg_api_key = ngx_var.arg_api_key
ngx.ctx.host = real_host
ngx.ctx.host_normalized = host_normalize(real_host)
ngx.ctx.http_x_api_key = ngx_var.http_x_api_key
ngx.ctx.port = real_port
ngx.ctx.protocol = real_proto
ngx.ctx.remote_addr = ngx_var.remote_addr
ngx.ctx.remote_user = ngx_var.remote_user
ngx.ctx.request_method = string.lower(ngx.var.request_method)

local request_uri = ngx_var.request_uri
ngx.ctx.original_request_uri = request_uri
ngx.ctx.request_uri = request_uri

-- Extract the path portion of the URL from request_uri (instead of
-- ngx.var.uri) so it's escaped as passed in (ngx.var.uri is unescaped).
local uri_path, _, gsub_err = ngx.re.gsub(request_uri, [[\?.*]], "", "jo")
if gsub_err then
  ngx.log(ngx.ERR, "regex error: ", gsub_err)
end
ngx.ctx.original_uri_path = uri_path
ngx.ctx.uri_path = uri_path

local function route()
  ngx.var.proxy_host_header = ngx.ctx.proxy_host
  ngx.var.proxy_request_uri = ngx.ctx.request_uri
  ngx.req.set_header("X-Forwarded-Proto", ngx.ctx.protocol)
  ngx.req.set_header("X-Forwarded-Port", ngx.ctx.port)
  ngx.req.set_header("X-Api-Umbrella-Backend-Server-Scheme", ngx.ctx.proxy_server_scheme)
  ngx.req.set_header("X-Api-Umbrella-Backend-Server-Host", ngx.ctx.proxy_server_host)
  ngx.req.set_header("X-Api-Umbrella-Backend-Server-Port", ngx.ctx.proxy_server_port)
end

local function route_to_api(api, url_match)
  redirect_matches_to_https(config["router"]["api_backend_required_https_regex_default"])

  ngx.ctx.matched_api = api
  ngx.ctx.matched_api_url_match = url_match

  route()
end

local function route_to_website(website)
  redirect_matches_to_https(website["website_backend_required_https_regex"] or config["router"]["website_backend_required_https_regex_default"])

  local host = website["backend_host"] or ngx.ctx.host
  ngx.ctx.proxy_host = host
  ngx.ctx.proxy_server_scheme = website["backend_protocol"] or "http"
  ngx.ctx.proxy_server_host = website["server_host"]
  ngx.ctx.proxy_server_port = website["server_port"]

  route()
end

local active_config = get_packed(ngx.shared.active_config, "packed_data") or {}

local api, url_match, api_err = api_matcher(active_config)
if api and url_match then
  route_to_api(api, url_match)
elseif api_err == "not_found" then
  local website, website_err = website_matcher(active_config)
  if website then
    route_to_website(website)
  else
    error_handler(website_err)
  end
else
  error_handler(api_err)
end
