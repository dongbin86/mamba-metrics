package mamba.timeline.cache;

import mamba.timeline.AbstractTimelineMetricsSink;
import mamba.timeline.TimelineMetrics;
import mamba.timeline.UnableToConnectException;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import static org.easymock.EasyMock.expect;
import static org.powermock.api.easymock.PowerMock.createNiceMock;
import static org.powermock.api.easymock.PowerMock.expectNew;
import static org.powermock.api.easymock.PowerMock.replayAll;

import java.io.IOException;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by sanbing on 10/10/16.
 */
@RunWith(PowerMockRunner.class)
@PrepareForTest({AbstractTimelineMetricsSink.class, URL.class, HttpURLConnection.class})
public class HandleConnectExceptionTest {
    private static final String COLLECTOR_URL = "collector";
    private TestTimelineMetricsSink sink;

    @Before
    public void init() {
        sink = new TestTimelineMetricsSink();
        OutputStream os = createNiceMock(OutputStream.class);
        HttpURLConnection connection = createNiceMock(HttpURLConnection.class);
        URL url = createNiceMock(URL.class);
        AbstractTimelineMetricsSink.NUMBER_OF_SKIPPED_COLLECTOR_EXCEPTIONS = 2;
        try {
            expectNew(URL.class, "collector").andReturn(url).anyTimes();
            expect(url.openConnection()).andReturn(connection).anyTimes();
            expect(connection.getOutputStream()).andReturn(os).anyTimes();
            expect(connection.getResponseCode()).andThrow(new IOException()).anyTimes();

            replayAll();
        } catch (Exception e) {
            //no-op
        }
    }

    @Test
    public void handleTest() {
        emitMetricsWithExpectedException(new TimelineMetrics());
        try {
            sink.emitMetrics(new TimelineMetrics());
        } catch (Exception e) {
            Assert.fail("There should be no exception");
        }
        emitMetricsWithExpectedException(new TimelineMetrics());
    }

    private void emitMetricsWithExpectedException(TimelineMetrics timelineMetrics) {
        try {
            sink.emitMetrics(timelineMetrics);
            Assert.fail();
        } catch (UnableToConnectException e) {
            Assert.assertEquals(COLLECTOR_URL, e.getConnectUrl());
        } catch (Exception e) {
            Assert.fail(e.getMessage());
        }
    }

    class TestTimelineMetricsSink extends AbstractTimelineMetricsSink {
        @Override
        protected String getCollectorUri() {
            return COLLECTOR_URL;
        }

        @Override
        protected int getTimeoutSeconds() {
            return 10;
        }

        @Override
        public boolean emitMetrics(TimelineMetrics metrics) {
            return super.emitMetrics(metrics);
        }
    }
}
