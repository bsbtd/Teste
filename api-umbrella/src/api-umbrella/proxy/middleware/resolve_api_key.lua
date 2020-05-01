local config = require "api-umbrella.proxy.models.file_config"
local is_empty = require("pl.types").is_empty

return function()
  local api_key_methods = config["gatekeeper"]["api_key_methods"]
  local api_key

  -- Find the API key in the header, query string, or HTTP auth.
  for _, method in ipairs(api_key_methods) do
    if method == "header" then
      api_key = ngx.ctx.http_x_api_key
    elseif method == "getParam" then
      api_key = ngx.ctx.arg_api_key
    elseif method == "basicAuthUsername" then
      api_key = ngx.ctx.remote_user
    end

    if not is_empty(api_key) then
      break
    end
  end

  -- Store the api key for logging.
  if not is_empty(api_key) then
    ngx.ctx.api_key = api_key
  end

  return nil
end
