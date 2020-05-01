require_relative "../test_helper"

class Test::AdminUi::TestStatsUsers < Minitest::Capybara::Test
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

  def test_xss_escaping_in_table
    user = FactoryBot.create(:xss_api_user)
    FactoryBot.create(:xss_log_item, {
      :request_at => Time.parse("2015-01-16T06:06:28.816Z").utc,
      :api_key => user.api_key,
      :user_id => user.id,
      :user_email => user.email,
      :user_registration_source => user.registration_source,
    })
    LogItem.refresh_indices!

    admin_login
    visit "/admin/#/stats/users?search=&start_at=2015-01-12&end_at=2015-01-18"
    refute_selector(".busy-blocker")

    assert_text(user.email)
    assert_text(user.first_name)
    assert_text(user.last_name)
    assert_text(user.use_description)
    refute_selector(".xss-test", :visible => :all)
  end

  def test_csv_download
    user = FactoryBot.create(:api_user, :email => "#{SecureRandom.uuid}@example.com")
    FactoryBot.create_list(:log_item, 5, {
      :request_at => Time.parse("2015-01-16T06:06:28.816Z").utc,
      :api_key => user.api_key,
      :user_id => user.id,
      :user_email => user.email,
      :user_registration_source => user.registration_source,
    })
    FactoryBot.create_list(:log_item, 5, :request_at => 1421413588000)
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
    visit "/admin/#/stats/users?search=&start_at=2015-01-12&end_at=2015-01-18"
    refute_selector(".busy-blocker")

    assert_link("Download CSV", :href => /start_at=2015-01-12/)
    link = find_link("Download CSV")
    uri = Addressable::URI.parse(link[:href])
    assert_equal("/admin/stats/users.csv", uri.path)
    assert_equal({
      "start_at" => "2015-01-12",
      "end_at" => "2015-01-18",
      "search" => "",
      "query" => default_query,
    }, uri.query_values)

    # Wait for the ajax actions to fetch the graph and tables to both
    # complete, or else the download link seems to be flakey in Capybara.
    assert_text("Download CSV")
    assert_text(user.email)
    refute_selector(".busy-blocker")
    click_link "Download CSV"

    file = download_file
    assert_equal(".csv", File.extname(file.path))
    csv = CSV.read(file.path)
    assert_equal([
      "Email",
      "First Name",
      "Last Name",
      "Website",
      "Registration Source",
      "Signed Up (UTC)",
      "Hits",
      "Last Request (UTC)",
      "Use Description",
    ], csv[0])
  end

  def test_date_range_picker
    assert_date_range_picker("/stats/users")
  end
end
