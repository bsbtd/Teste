local path = require "pl.path"
local setup = require "api-umbrella.cli.setup"
local shell_blocking_capture_combined = require("shell-games").capture_combined
local status = require "api-umbrella.cli.status"
local types = require "pl.types"

local is_empty = types.is_empty

local function reload_perp(perp_base)
  local _, err = shell_blocking_capture_combined({ "perphup", perp_base })
  if err then
    print("Failed to reload perp\n" .. err)
    os.exit(1)
  end
end

local function reload_web_delayed_job(perp_base)
  local _, err = shell_blocking_capture_combined({ "perpctl", "-b", perp_base, "int", "web-delayed-job" })
  if err then
    print("Failed to reload web-delayed-job\n" .. err)
    os.exit(1)
  end
end

local function reload_web_puma(perp_base)
  local _, err = shell_blocking_capture_combined({ "perpctl", "-b", perp_base, "2", "web-puma" })
  if err then
    print("Failed to reload web-puma\n" .. err)
    os.exit(1)
  end
end

local function reload_trafficserver(config)
  local _, err = shell_blocking_capture_combined({ "env", "TS_ROOT=" .. config["root_dir"], "traffic_ctl", "config", "reload" })
  if err then
    print("Failed to reload trafficserver\n" .. err)
    os.exit(1)
  end
end

local function reload_nginx(perp_base)
  local _, err = shell_blocking_capture_combined({ "perpctl", "-b", perp_base, "hup", "nginx" })
  if err then
    print("Failed to reload nginx\n" .. err)
    os.exit(1)
  end
end

local function reload_geoip_auto_updater(perp_base)
  local _, err = shell_blocking_capture_combined({ "perpctl", "-b", perp_base, "term", "geoip-auto-updater" })
  if err then
    print("Failed to reload geoip-auto-updater\n" .. err)
    os.exit(1)
  end
end

local function reload_dev_env_ember_server(perp_base)
  local _, err = shell_blocking_capture_combined({ "perpctl", "-b", perp_base, "term", "dev-env-ember-server" })
  if err then
    print("Failed to reload dev-env-ember-server\n" .. err)
    os.exit(1)
  end
end

return function(options)
  options["reload"] = nil

  local running = status()
  if not running then
    print("api-umbrella is stopped")
    os.exit(7)
  end

  local config = setup()
  local perp_base = path.join(config["etc_dir"], "perp")

  reload_perp(perp_base)

  if config["_service_web_enabled?"] and (is_empty(options) or options["web"]) then
    reload_web_delayed_job(perp_base)
    reload_web_puma(perp_base)
  end

  if config["_service_router_enabled?"] and (is_empty(options) or options["router"]) then
    reload_trafficserver(config)
    reload_nginx(perp_base)

    if config["geoip"]["_enabled"] then
      reload_geoip_auto_updater(perp_base)
    end
  end

  if config["app_env"] == "development" then
    reload_dev_env_ember_server(perp_base)
  end
end
