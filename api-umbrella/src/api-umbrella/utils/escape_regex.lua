-- https://github.com/benjamingr/RegExp.escape/blob/master/polyfill.js
return function(value)
  local result, _, gsub_err = ngx.re.gsub(value, "[\\\\^$*+?.()|[\\]{}]", "\\$0", "jo")
  if gsub_err then
    ngx.log(ngx.ERR, "regex error: ", gsub_err)
  end

  return result
end
