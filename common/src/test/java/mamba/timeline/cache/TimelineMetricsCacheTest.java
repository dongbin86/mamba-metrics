package mamba.timeline.cache;

import mamba.timeline.TimelineMetric;
import org.junit.Test;

import java.util.Map;
import java.util.TreeMap;

import static org.junit.Assert.*;

/**
 * Created by sanbing on 10/10/16.
 */
public class TimelineMetricsCacheTest {

    private static final long DEFAULT_START_TIME = 1411023766;
    private static final String METRIC_NAME = "Test name";
    private static final double delta = 0.00001;

    private final TimelineMetricsCache timelineMetricsCache =
            new TimelineMetricsCache(TimelineMetricsCache.MAX_RECS_PER_NAME_DEFAULT,
                    TimelineMetricsCache.MAX_EVICTION_TIME_MILLIS);

    @Test
    public void testPutGetCounterTimelineMetric() throws Exception {
        TimelineMetric metric = createTimelineMetric(
                new TreeMap<Long, Double>() {{put(1L, 10.0);}}, DEFAULT_START_TIME);

        timelineMetricsCache.putTimelineMetric(metric, true);
        metric = createTimelineMetric(new TreeMap<Long, Double>() {{
            put(2L, 10.0);
            put(3L, 20.0);
            put(4L, 30.0);
        }}, DEFAULT_START_TIME + 2 * TimelineMetricsCache.MAX_EVICTION_TIME_MILLIS);
        timelineMetricsCache.putTimelineMetric(metric, true);
        TimelineMetric cachedMetric
                = timelineMetricsCache.getTimelineMetric(METRIC_NAME);
        assertEquals(0, cachedMetric.getMetricValues().get(1L), delta);
        assertEquals(0, cachedMetric.getMetricValues().get(2L), delta);
        assertEquals(10, cachedMetric.getMetricValues().get(3L), delta);
        assertEquals(10, cachedMetric.getMetricValues().get(4L), delta);

        metric = createTimelineMetric(new TreeMap<Long, Double>() {{
            put(5L, 100.0);
            put(6L, 120.0);
            put(7L, 230.0);
        }}, DEFAULT_START_TIME + 3 * TimelineMetricsCache.MAX_EVICTION_TIME_MILLIS);
        timelineMetricsCache.putTimelineMetric(metric, true);
        metric = createTimelineMetric(new TreeMap<Long, Double>() {{
            put(8L, 300.0);
        }}, DEFAULT_START_TIME + 5 * TimelineMetricsCache.MAX_EVICTION_TIME_MILLIS);

        timelineMetricsCache.putTimelineMetric(metric, true);
        cachedMetric = timelineMetricsCache.getTimelineMetric(METRIC_NAME);
        assertEquals(70, cachedMetric.getMetricValues().get(5L), delta);
        assertEquals(20, cachedMetric.getMetricValues().get(6L), delta);
        assertEquals(110, cachedMetric.getMetricValues().get(7L), delta);
        assertEquals(70, cachedMetric.getMetricValues().get(8L), delta);
    }

    @Test
    public void testMaxRecsPerName() throws Exception {
        int maxRecsPerName = 2;
        int maxEvictionTime = TimelineMetricsCache.MAX_EVICTION_TIME_MILLIS ;
        TimelineMetricsCache timelineMetricsCache =
                new TimelineMetricsCache(maxRecsPerName, maxEvictionTime);

        // put 2 metrics , no cache overflow
        timelineMetricsCache.putTimelineMetric(
                createTimelineMetricSingleValue(DEFAULT_START_TIME ));
        timelineMetricsCache.putTimelineMetric(
                createTimelineMetricSingleValue(DEFAULT_START_TIME + maxEvictionTime * 2));
        TimelineMetric cachedMetric = timelineMetricsCache.getTimelineMetric(METRIC_NAME);
        assertNotNull(cachedMetric);
        assertFalse(cachedMetric.getMetricValues().isEmpty());
        assertEquals("2 values added.", 2, cachedMetric.getMetricValues().size());
        assertEquals(DEFAULT_START_TIME, cachedMetric.getStartTime());

        // put 3 metrics, no cache overflow. check is performed before put operation
        timelineMetricsCache.putTimelineMetric(
                createTimelineMetricSingleValue(DEFAULT_START_TIME ));
        timelineMetricsCache.putTimelineMetric(
                createTimelineMetricSingleValue(DEFAULT_START_TIME + maxEvictionTime * 2));
        timelineMetricsCache.putTimelineMetric(
                createTimelineMetricSingleValue(DEFAULT_START_TIME + maxEvictionTime * 3));
        cachedMetric = timelineMetricsCache.getTimelineMetric(METRIC_NAME);
        assertNotNull(cachedMetric);
        assertFalse(cachedMetric.getMetricValues().isEmpty());
        assertEquals("3 values added.", 3, cachedMetric.getMetricValues().size());
        assertEquals(DEFAULT_START_TIME, cachedMetric.getStartTime());

        // put 4 metric values, cache cleaned.
        timelineMetricsCache.putTimelineMetric(
                createTimelineMetricSingleValue(DEFAULT_START_TIME ));
        timelineMetricsCache.putTimelineMetric(
                createTimelineMetricSingleValue(DEFAULT_START_TIME + maxEvictionTime * 2));
        timelineMetricsCache.putTimelineMetric(
                createTimelineMetricSingleValue(DEFAULT_START_TIME + maxEvictionTime * 3));
        timelineMetricsCache.putTimelineMetric(
                createTimelineMetricSingleValue(DEFAULT_START_TIME + maxEvictionTime * 4));
        cachedMetric = timelineMetricsCache.getTimelineMetric(METRIC_NAME);
        assertNotNull(cachedMetric);
        assertFalse(cachedMetric.getMetricValues().isEmpty());
        // check is performed before put operation. while putting 4th metric value,
        // the first value deleted
        assertEquals("1 metric value should have been removed", 3, cachedMetric.getMetricValues().size());
        // first metric value was removed, starttime == second metric value starttime
        assertEquals(DEFAULT_START_TIME + maxEvictionTime * 2, cachedMetric.getStartTime());
    }

    private TimelineMetric createTimelineMetricSingleValue(final long startTime) {
        TreeMap<Long, Double> values = new TreeMap<Long, Double>();
        values.put(startTime, 0.0);
        return createTimelineMetric(values, startTime);

    }
    private TimelineMetric createTimelineMetric(Map<Long, Double> metricValues,
                                                long startTime) {
        TimelineMetric timelineMetric = new TimelineMetric();
        timelineMetric.setMetricName(METRIC_NAME);
        timelineMetric.setHostName("Test hostName");
        timelineMetric.setAppId("test serviceName");
        timelineMetric.setStartTime(startTime);
        timelineMetric.setType("Number");
        timelineMetric.setMetricValues(new TreeMap<Long, Double>(metricValues));
        return timelineMetric;
    }

}