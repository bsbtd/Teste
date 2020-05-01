local config = require "api-umbrella.proxy.models.file_config"
local json_decode = require("cjson").decode
local lustache = require "lustache"
local mustache_unescape = require "api-umbrella.utils.mustache_unescape"
local xpcall_error_handler = require "api-umbrella.utils.xpcall_error_handler"

local elasticsearch_templates

local path = os.getenv("API_UMBRELLA_SRC_ROOT") .. "/config/elasticsearch_templates_v" .. config["elasticsearch"]["template_version"]
if config["elasticsearch"]["api_version"] >= 7 then
  path = path .. "_es7.json.mustache"
elseif config["elasticsearch"]["api_version"] >= 5 then
  path = path .. "_es5.json.mustache"
elseif config["elasticsearch"]["api_version"] >= 2 then
  path = path .. "_es2.json.mustache"
else
  error("Unsupported version of elasticsearch: " .. (config["elasticsearch"]["api_version"] or ""))
end

local f, err = io.open(path, "rb")
if err then
  ngx.log(ngx.ERR, "failed to open file: ", err)
else
  local content = f:read("*all")
  if content then
    content = lustache:render(mustache_unescape(content), config)

    local ok, data = xpcall(json_decode, xpcall_error_handler, content)
    if ok then
      elasticsearch_templates = data

      -- In the test environment, disable replicas and reduce shards to speed
      -- things up.
      if config["app_env"] == "test" then
        for _, template in ipairs(elasticsearch_templates) do
          if not template["template"]["settings"] then
            template["template"]["settings"] = {}
          end
          if not template["template"]["settings"]["index"] then
            template["template"]["settings"]["index"] = {}
          end

          template["template"]["settings"]["index"]["number_of_shards"] = 1
          template["template"]["settings"]["index"]["number_of_replicas"] = 0
        end
      end
    else
      ngx.log(ngx.ERR, "failed to parse json for " .. (path or "") .. ": " .. (data or ""))
    end
  end

  f:close()
end

return elasticsearch_templates
