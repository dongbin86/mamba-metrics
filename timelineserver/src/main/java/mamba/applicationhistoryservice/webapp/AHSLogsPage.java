package mamba.applicationhistoryservice.webapp;

import org.apache.hadoop.yarn.webapp.SubView;
import org.apache.hadoop.yarn.webapp.log.AggregatedLogsBlock;

import static org.apache.hadoop.yarn.webapp.YarnWebParams.CONTAINER_ID;
import static org.apache.hadoop.yarn.webapp.YarnWebParams.ENTITY_STRING;

/**
 * Created by dongbin on 2016/10/10.
 */
public class AHSLogsPage extends AHSView {
    /*
     * (non-Javadoc)
     *
     * @see
     * org.apache.hadoop.yarn.server.applicationhistoryservice.webapp.AHSView#
     * preHead(org.apache.hadoop .yarn.webapp.hamlet.Hamlet.HTML)
     */
    @Override
    protected void preHead(Page.HTML<_> html) {
        String logEntity = $(ENTITY_STRING);
        if (logEntity == null || logEntity.isEmpty()) {
            logEntity = $(CONTAINER_ID);
        }
        if (logEntity == null || logEntity.isEmpty()) {
            logEntity = "UNKNOWN";
        }
        commonPreHead(html);
    }

    @Override
    protected Class<? extends SubView> content() {
        return AggregatedLogsBlock.class;
    }
}
