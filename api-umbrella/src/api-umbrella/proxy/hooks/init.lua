-- Pre-load modules.
require "api-umbrella.proxy.hooks.init_preload_modules"

-- Generate a unique ID to represent this group of worker processes. This value
-- will be the same amongst all the subsequently inited workers, but the value
-- will differ for each new group of worker processes that get started when
-- nginx is reloaded (SIGHUP).
--
-- This is used to deal with race conditions during reloads between the nginx
-- workers shutting down and the new workers starting up.
local incr_err
WORKER_GROUP_ID, incr_err = ngx.shared.active_config:incr("worker_group_id", 1)
if incr_err == "not found" then
  WORKER_GROUP_ID = 1
  local set_ok, set_err = ngx.shared.active_config:safe_set("worker_group_id", 1)
  if not set_ok then
    ngx.log(ngx.ERR, "failed to set 'worker_group_id' in 'active_config' shared dict: ", set_err)
    return
  end
elseif incr_err then
  ngx.log(ngx.ERR, "worker_group_id incr err: ", incr_err)
end

ngx.shared.stats:delete("distributed_last_fetched_at")
ngx.shared.api_users:delete("last_fetched_at")
local set_ok, set_err = ngx.shared.active_config:safe_set("elasticsearch_templates_created", false)
if not set_ok then
  ngx.log(ngx.ERR, "failed to set 'elasticsearch_templates_created' in 'active_config' shared dict: ", set_err)
end
