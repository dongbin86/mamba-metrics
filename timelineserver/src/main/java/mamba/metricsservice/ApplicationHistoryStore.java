package mamba.metricsservice;

import org.apache.hadoop.service.Service;

/**
 * Created by sanbing on 10/10/16.
 */
public interface ApplicationHistoryStore extends Service,
        ApplicationHistoryReader, ApplicationHistoryWriter {
}
