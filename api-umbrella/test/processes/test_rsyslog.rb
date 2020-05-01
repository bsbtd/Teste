require_relative "../test_helper"

class Test::Processes::TestRsyslog < Minitest::Test
  include ApiUmbrellaTestHelpers::Setup
  include ApiUmbrellaTestHelpers::Logging

  def setup
    super
    setup_server
  end

  # To make sure rsyslog doesn't leak memory while logging requests:
  # https://github.com/rsyslog/rsyslog/issues/1668
  def test_memory_leak
    # Make some initial requests, to ensure rsyslog is warmed up, which should
    # stabilize its memory use.
    make_requests(2_000)
    warmed_memory_use = memory_use
    warmed_error_log_size = elasticsearch_error_log_size

    # Validate that the rsyslog process isn't using more than 100MB initially.
    # If it is, then this is probably the best indicator that there's a memory
    # leak and previous tests in the test suite have already leaked a lot of
    # memory. Typically rsyslog is more in the 20MB-50MB range.
    assert_operator(warmed_memory_use[:rss], :<, 100_000)

    # Make more requests.
    make_requests(10_000)
    final_memory_use = memory_use
    final_error_log_size = elasticsearch_error_log_size

    # Compare the memory use before and after making requests. Note that this
    # is a very loose test (allowing 15MB of growth), since there can be a
    # decent amount of fluctuation in normal use. It's hard to determine
    # whether there's really a leak in such a short test, so the better test is
    # actually the initial check to ensure the process is less than 100MB after
    # warmup. Assuming this test gets called as part of running the full test
    # suite, and it's not one of the initial tests (which is randomized, but
    # won't be at least enough times to catch this at some point), then that's
    # a better test for the leak, since it allows us to detect leaks over
    # longer periods of time (over the entire time the test suite is running,
    # rather than just in this isolated test).
    rss_diff = final_memory_use.fetch(:rss) - warmed_memory_use.fetch(:rss)
    assert_operator(rss_diff, :<=, 15_000)

    # Ensure nothing was generated in the elasticsearch error log file, since
    # the specific leak in v8.28.0 was triggered by generated error data.
    assert_equal(warmed_error_log_size, final_error_log_size)
  end

  private

  def make_requests(count)
    request = nil
    hydra = Typhoeus::Hydra.new
    count.times do
      request = Typhoeus::Request.new("http://127.0.0.1:9080/api/hello/", log_http_options)
      hydra.queue(request)
    end
    hydra.run

    # Just check for the last request made and make sure it gets logged.
    assert_response_code(200, request.response)
    wait_for_log(request.response)
  end

  def memory_use
    pid = File.read(File.join($config["run_dir"], "rsyslogd.pid"))
    output, status = run_shell("ps", "-o", "vsz=,rss=", pid)
    assert_equal(0, status, output)

    columns = output.strip.split(/\s+/)
    assert_equal(2, columns.length, columns)

    memory = {
      :vsz => Integer(columns[0]),
      :rss => Integer(columns[1]),
    }

    assert_operator(memory[:vsz], :>, 0)
    assert_operator(memory[:rss], :>, 0)

    memory
  end

  def elasticsearch_error_log_size
    size = 0
    path = File.join($config["log_dir"], "rsyslog/elasticsearch_error.log")
    if(File.exist?(path))
      size = File.size(path)
    end

    size
  end
end
