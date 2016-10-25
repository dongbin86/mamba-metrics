package mamba.metricsservice.timeline;


import org.apache.hadoop.service.Service;
/**
 * Created by dongbin on 2016/10/10.
 */
public interface TimelineStore extends
        Service, TimelineReader, TimelineWriter {
}
