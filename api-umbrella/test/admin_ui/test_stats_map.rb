require_relative "../test_helper"

class Test::AdminUi::TestStatsMap < Minitest::Capybara::Test
  include Capybara::Screenshot::MiniTestPlugin
  include ApiUmbrellaTestHelpers::AdminAuth
  include ApiUmbrellaTestHelpers::DateRangePicker
  include ApiUmbrellaTestHelpers::Downloads
  include ApiUmbrellaTestHelpers::Setup

  def setup
    super
    setup_server
    LogItem.clean_indices!
  end

  def test_csv_download
    FactoryBot.create_list(:log_item, 5, :request_at => Time.parse("2015-01-16T06:06:28.816Z").utc, :request_ip_country => "US")
    FactoryBot.create_list(:log_item, 5, :request_at => 1421413588000, :request_ip_country => "CI")
    LogItem.refresh_indices!
    default_query = JSON.generate({
      "condition" => "AND",
      "rules" => [{
        "field" => "gatekeeper_denied_code",
        "id" => "gatekeeper_denied_code",
        "input" => "select",
        "operator" => "is_null",
        "type" => "string",
        "value" => nil,
      }],
    })

    admin_login
    visit "/admin/#/stats/map?search=&start_at=2015-01-12&end_at=2015-01-18"
    refute_selector(".busy-blocker")

    assert_link("Download CSV", :href => /start_at=2015-01-12/)
    link = find_link("Download CSV")
    uri = Addressable::URI.parse(link[:href])
    assert_equal("/admin/stats/map.csv", uri.path)
    assert_equal({
      "start_at" => "2015-01-12",
      "end_at" => "2015-01-18",
      "search" => "",
      "query" => default_query,
      "region" => "world",
    }, uri.query_values)

    # Wait for the ajax actions to fetch the graph and tables to both
    # complete, or else the download link seems to be flakey in Capybara.
    assert_text("Download CSV")
    assert_text("United States")
    assert_text("Côte d'Ivoire")
    refute_selector(".busy-blocker")
    click_link "Download CSV"

    file = download_file
    assert_equal(".csv", File.extname(file.path))
    csv = CSV.read(file.path, :encoding => "iso8859-1")
    assert_equal(["Location", "Hits"], csv[0])
  end

  def test_date_range_picker
    assert_date_range_picker("/stats/map")
  end
end
