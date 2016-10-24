package mamba.applicationhistoryservice;


import mamba.applicationhistoryservice.metrics.timeline.HBaseTimelineMetricStore;
import mamba.applicationhistoryservice.metrics.timeline.TimelineMetricConfiguration;
import mamba.applicationhistoryservice.metrics.timeline.TimelineMetricStore;
import mamba.applicationhistoryservice.timeline.LeveldbTimelineStore;
import mamba.applicationhistoryservice.timeline.MemoryTimelineStore;
import mamba.applicationhistoryservice.timeline.TimelineStore;
import mamba.applicationhistoryservice.webapp.AHSWebApp;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.http.HttpConfig;
import org.apache.hadoop.metrics2.lib.DefaultMetricsSystem;
import org.apache.hadoop.metrics2.source.JvmMetrics;
import org.apache.hadoop.service.CompositeService;
import org.apache.hadoop.util.ExitUtil;
import org.apache.hadoop.util.ReflectionUtils;
import org.apache.hadoop.util.ShutdownHookManager;
import org.apache.hadoop.util.StringUtils;
import org.apache.hadoop.yarn.YarnUncaughtExceptionHandler;
import org.apache.hadoop.yarn.conf.YarnConfiguration;
import org.apache.hadoop.yarn.exceptions.YarnRuntimeException;
import org.apache.hadoop.yarn.webapp.WebApp;
import org.apache.hadoop.yarn.webapp.WebApps;

import static mamba.applicationhistoryservice.metrics.timeline.TimelineMetricConfiguration.DISABLE_APPLICATION_TIMELINE_STORE;

/**
 * Created by dongbin on 2016/10/10.
 */
public class MambaMetricsServer extends CompositeService {

    public static final int SHUTDOWN_HOOK_PRIORITY = 30;
    private static final Log LOG = LogFactory.getLog(MambaMetricsServer.class);

    ApplicationHistoryClientService ahsClientService;

    ApplicationHistoryManager historyManager;

    TimelineStore timelineStore;

    TimelineMetricStore timelineMetricStore;

    private WebApp webApp;

    private TimelineMetricConfiguration metricConfiguration;

    public MambaMetricsServer() {
        super(MambaMetricsServer.class.getName());
    }

    static MambaMetricsServer launchMetricsServer(String[] args) {
        Thread.setDefaultUncaughtExceptionHandler(new YarnUncaughtExceptionHandler());
        StringUtils.startupShutdownMessage(MambaMetricsServer.class, args, LOG);
        MambaMetricsServer mambaMetricsServer = null;
        try {
            mambaMetricsServer = new MambaMetricsServer();
            ShutdownHookManager.get().addShutdownHook(
                    new CompositeServiceShutdownHook(mambaMetricsServer),
                    SHUTDOWN_HOOK_PRIORITY);
            YarnConfiguration conf = new YarnConfiguration();
            mambaMetricsServer.init(conf);
            mambaMetricsServer.start();
        } catch (Throwable t) {
            LOG.fatal("Error starting MambaMetricsServer", t);
            ExitUtil.terminate(-1, "Error starting MambaMetricsServer");
        }
        return mambaMetricsServer;
    }

    public static void main(String[] args) {
        launchMetricsServer(args);
    }

    @Override
    protected void serviceInit(Configuration conf) throws Exception {
        metricConfiguration = new TimelineMetricConfiguration();
        metricConfiguration.initialize();
        /**加载hbas-site.xml和ams-site.xml 环境变量中必须要有，否则报错*//*
        historyManager = createApplicationHistory();
        *//**historymanager(如果只是作metrics存储，则不需要）*//*
        ahsClientService = createApplicationHistoryClientService(historyManager);
        *//**同样可以去掉这个部分*//*
        addService(ahsClientService);
        addService((Service) historyManager);*/
        /**重点就是下面两个timelineStore和timelineMetricsStore*/
        timelineStore = createTimelineStore(conf);
        timelineMetricStore = createTimelineMetricStore(conf);
        addIfService(timelineStore);
        addIfService(timelineMetricStore);
        super.serviceInit(conf);
    }

    @Override
    protected void serviceStart() throws Exception {
        DefaultMetricsSystem.initialize("MambaMetricsServer");
        JvmMetrics.initSingleton("MambaMetricsServer", null);

        startWebApp();
        super.serviceStart();
    }

    @Override
    protected void serviceStop() throws Exception {
        if (webApp != null) {
            webApp.stop();
        }

        DefaultMetricsSystem.shutdown();
        super.serviceStop();
    }

    /*public ApplicationHistoryClientService getClientService() {
        return this.ahsClientService;
    }

    protected ApplicationHistoryClientService createApplicationHistoryClientService(
            ApplicationHistoryManager historyManager) {
        return new ApplicationHistoryClientService(historyManager, metricConfiguration);
    }

    protected ApplicationHistoryManager createApplicationHistory() {
        return new ApplicationHistoryManagerImpl();
    }

    protected ApplicationHistoryManager getApplicationHistory() {
        return this.historyManager;
    }

    protected ApplicationHistoryManager createApplicationHistoryManager(
            Configuration conf) {
        return new ApplicationHistoryManagerImpl();
    }
*/
    protected TimelineStore createTimelineStore(Configuration conf) {
        if (conf.getBoolean(DISABLE_APPLICATION_TIMELINE_STORE, true)) {
            LOG.info("Explicitly disabled application timeline store.");
            return new MemoryTimelineStore();
        }/**
         timeline.service.disable.application.timeline.store=true，那么就直接使用内存做timelinestore
         */
        return ReflectionUtils.newInstance(conf.getClass(
                YarnConfiguration.TIMELINE_SERVICE_STORE, LeveldbTimelineStore.class,
                TimelineStore.class), conf);
        /**默认使用LeveldbTimelineStore*/
    }

    protected TimelineMetricStore createTimelineMetricStore(Configuration conf) {
        LOG.info("Creating metrics store.");
        return new HBaseTimelineMetricStore(metricConfiguration);
    }/**TimelineMetricStore则是hbase默认实现*/

    protected void startWebApp() {
        String bindAddress = metricConfiguration.getWebappAddress();
        LOG.info("Instantiating AHSWebApp at " + bindAddress);
        try {
            Configuration conf = metricConfiguration.getMetricsConf();
            conf.set("hadoop.http.max.threads", String.valueOf(metricConfiguration
                    .getTimelineMetricsServiceHandlerThreadCount()));
            HttpConfig.Policy policy = HttpConfig.Policy.valueOf(
                    conf.get(TimelineMetricConfiguration.TIMELINE_SERVICE_HTTP_POLICY,
                            HttpConfig.Policy.HTTP_ONLY.name()));

            /**
             * this.name = "applicationhistory";
             this.api = "ApplicationHistoryClientService";
             this.application = "ahsClientService";
             this.wsName = "ws";
             * */
            webApp =WebApps.$for("applicationhistory", ApplicationHistoryClientService.class,
                                    ahsClientService, "ws")
                            .withHttpPolicy(conf, policy)
                            .at(bindAddress)
                            .start(new AHSWebApp(timelineStore, timelineMetricStore,
                                    ahsClientService));
        } catch (Exception e) {
            String msg = "AHSWebApp failed to start.";
            LOG.error(msg, e);
            throw new YarnRuntimeException(msg, e);
        }
    }

    public TimelineStore getTimelineStore() {
        return timelineStore;
    }

}
