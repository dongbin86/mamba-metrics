package mamba.applicationhistoryservice.webapp;

import mamba.applicationhistoryservice.ApplicationHistoryClientService;
import mamba.applicationhistoryservice.metrics.timeline.TimelineMetricStore;
import mamba.applicationhistoryservice.timeline.TimelineStore;
import org.apache.hadoop.yarn.api.ApplicationBaseProtocol;
import org.apache.hadoop.yarn.webapp.GenericExceptionHandler;
import org.apache.hadoop.yarn.webapp.WebApp;
import org.apache.hadoop.yarn.webapp.YarnJacksonJaxbJsonProvider;
import org.apache.hadoop.yarn.webapp.YarnWebParams;

import static org.apache.hadoop.yarn.util.StringHelper.pajoin;

/**
 * Created by dongbin on 2016/10/10.
 */
public class AHSWebApp extends WebApp implements YarnWebParams {

    private final TimelineStore timelineStore;
    private final TimelineMetricStore timelineMetricStore;
    private final ApplicationHistoryClientService historyClientService;

    public AHSWebApp(TimelineStore timelineStore,
                     TimelineMetricStore timelineMetricStore,
                     ApplicationHistoryClientService historyClientService) {

        this.timelineStore = timelineStore;
        this.timelineMetricStore = timelineMetricStore;
        this.historyClientService = historyClientService;
    }

    @Override
    public void setup() {
        bind(YarnJacksonJaxbJsonProvider.class);
        bind(AHSWebServices.class);
        bind(TimelineWebServices.class);
        bind(GenericExceptionHandler.class);
        bind(ApplicationBaseProtocol.class).toInstance(historyClientService);
        bind(TimelineStore.class).toInstance(timelineStore);
        bind(TimelineMetricStore.class).toInstance(timelineMetricStore);
        route("/", AHSController.class);
        route(pajoin("/apps", APP_STATE), AHSController.class);
        route(pajoin("/app", APPLICATION_ID), AHSController.class, "app");
        route(pajoin("/appattempt", APPLICATION_ATTEMPT_ID), AHSController.class,
                "appattempt");
        route(pajoin("/container", CONTAINER_ID), AHSController.class, "container");
        route(pajoin("/logs", NM_NODENAME, CONTAINER_ID, ENTITY_STRING, APP_OWNER,
                        CONTAINER_LOG_TYPE), AHSController.class, "logs");
    }
}