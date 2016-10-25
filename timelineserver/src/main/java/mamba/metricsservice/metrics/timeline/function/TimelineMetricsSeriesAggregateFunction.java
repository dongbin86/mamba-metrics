package mamba.metricsservice.metrics.timeline.function;

import mamba.timeline.TimelineMetric;
import mamba.timeline.TimelineMetrics;

/**
 * Created by dongbin on 2016/10/10.
 */
public interface TimelineMetricsSeriesAggregateFunction {
    TimelineMetric apply(TimelineMetrics timelineMetrics);
}
