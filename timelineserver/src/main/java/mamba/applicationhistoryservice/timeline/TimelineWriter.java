package mamba.applicationhistoryservice.timeline;

import org.apache.hadoop.yarn.api.records.timeline.TimelineEntities;
import org.apache.hadoop.yarn.api.records.timeline.TimelinePutResponse;

import java.io.IOException;

/**
 * Created by dongbin on 2016/10/10.
 */
public interface TimelineWriter {


  TimelinePutResponse put(TimelineEntities data) throws IOException;

}
