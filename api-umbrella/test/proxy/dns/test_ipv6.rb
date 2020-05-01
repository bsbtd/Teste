require_relative "../../test_helper"

class Test::Proxy::Dns::TestIpv6 < Minitest::Test
  include ApiUmbrellaTestHelpers::Setup
  include ApiUmbrellaTestHelpers::Dns

  def setup
    super
    setup_server
  end

  def test_defaults_to_no_ipv6
    override_config({
      "dns_resolver" => {
        "nameservers" => ["[127.0.0.1]:#{$config["unbound"]["port"]}"],
        "max_stale" => 0,
        "negative_ttl" => false,
      },
    }, "--router") do
      set_dns_records(["#{unique_test_hostname} 60 AAAA ::1"])

      prepend_api_backends([
        {
          :frontend_host => "127.0.0.1",
          :backend_host => unique_test_hostname,
          :servers => [{ :host => unique_test_hostname, :port => 9444 }],
          :url_matches => [{ :frontend_prefix => "/#{unique_test_id}/", :backend_prefix => "/info/" }],
        },
      ]) do
        response = Typhoeus.get("http://127.0.0.1:9080/#{unique_test_id}/", http_options)
        assert_response_code(502, response)
      end
    end
  end

  def test_allow_ipv6_enabled
    skip_unless_ipv6_support

    override_config({
      "dns_resolver" => {
        "nameservers" => ["[127.0.0.1]:#{$config["unbound"]["port"]}"],
        "max_stale" => 0,
        "negative_ttl" => false,
        "allow_ipv6" => true,
      },
    }, "--router") do
      set_dns_records(["#{unique_test_hostname} 60 AAAA ::1"])

      prepend_api_backends([
        {
          :frontend_host => "127.0.0.1",
          :backend_host => unique_test_hostname,
          :servers => [{ :host => unique_test_hostname, :port => 9444 }],
          :url_matches => [{ :frontend_prefix => "/#{unique_test_id}/", :backend_prefix => "/info/" }],
        },
      ]) do
        response = Typhoeus.get("http://127.0.0.1:9080/#{unique_test_id}/", http_options)
        assert_response_code(200, response)
        data = MultiJson.load(response.body)
        assert_equal("::1", data["local_interface_ip"])
      end
    end
  end
end
