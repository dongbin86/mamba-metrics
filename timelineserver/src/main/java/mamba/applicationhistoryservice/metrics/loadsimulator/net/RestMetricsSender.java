package mamba.applicationhistoryservice.metrics.loadsimulator.net;


import com.google.common.base.Stopwatch;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

/**
 * Created by sanbing on 10/10/16.
 */
public class RestMetricsSender implements MetricsSender {
    private final static Logger LOG = LoggerFactory.getLogger(RestMetricsSender.class);

    private final static String COLLECTOR_URL = "http://%s/ws/v1/timeline/metrics";
    private final String collectorServiceAddress;


    public RestMetricsSender(String metricsHost) {
        collectorServiceAddress = String.format(COLLECTOR_URL, metricsHost);
    }


    @Override
    public String pushMetrics(String payload) {
        String responseString = "";
        UrlService svc = null;
        Stopwatch timer = new Stopwatch().start();

        try {
            LOG.info("server: {}", collectorServiceAddress);

            svc = getConnectedUrlService();
            responseString = svc.send(payload);

            timer.stop();
            LOG.info("http response time: " + timer.elapsedTime(TimeUnit.MILLISECONDS)
                    + " ms");

            if (responseString.length() > 0) {
                LOG.debug("POST response from server: " + responseString);
            }
        } catch (IOException e) {
            LOG.error("", e);
        } finally {
            if (svc != null) {
                svc.disconnect();
            }
        }

        return responseString;
    }

    /**
     * Relaxed to protected for testing.
     */
    protected UrlService getConnectedUrlService() throws IOException {
        return UrlService.newConnection(collectorServiceAddress);
    }

}
