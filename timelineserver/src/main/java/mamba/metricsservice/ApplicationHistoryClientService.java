package mamba.metricsservice;

import mamba.metricsservice.metrics.timeline.TimelineMetricConfiguration;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.ipc.Server;
import org.apache.hadoop.net.NetUtils;
import org.apache.hadoop.service.AbstractService;
import org.apache.hadoop.yarn.api.ApplicationHistoryProtocol;
import org.apache.hadoop.yarn.api.protocolrecords.*;
import org.apache.hadoop.yarn.api.records.ApplicationAttemptReport;
import org.apache.hadoop.yarn.api.records.ApplicationId;
import org.apache.hadoop.yarn.api.records.ApplicationReport;
import org.apache.hadoop.yarn.api.records.ContainerReport;
import org.apache.hadoop.yarn.conf.YarnConfiguration;
import org.apache.hadoop.yarn.exceptions.ApplicationAttemptNotFoundException;
import org.apache.hadoop.yarn.exceptions.ApplicationNotFoundException;
import org.apache.hadoop.yarn.exceptions.ContainerNotFoundException;
import org.apache.hadoop.yarn.exceptions.YarnException;
import org.apache.hadoop.yarn.ipc.YarnRPC;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.util.ArrayList;

/**
 * Created by sanbing on 10/10/16.
 */
public class ApplicationHistoryClientService extends AbstractService implements
        ApplicationHistoryProtocol {

    private static final Log LOG = LogFactory.getLog(ApplicationHistoryClientService.class);
    private ApplicationHistoryManager history;
    private Server server;
    private InetSocketAddress bindAddress;
    private TimelineMetricConfiguration metricConfiguration;

    public ApplicationHistoryClientService(ApplicationHistoryManager history) {
        super("ApplicationHistoryClientService");
        this.history = history;
    }

    public ApplicationHistoryClientService(ApplicationHistoryManager history,
                                           TimelineMetricConfiguration metricConfiguration) {
        this(history);
        this.metricConfiguration = metricConfiguration;
    }

    protected void serviceStart() throws Exception {
        Configuration conf = getConfig();
        YarnRPC rpc = YarnRPC.create(conf);
        InetSocketAddress address =
                NetUtils.createSocketAddr(metricConfiguration.getTimelineServiceRpcAddress(),
                        YarnConfiguration.DEFAULT_TIMELINE_SERVICE_PORT);

        server =
                rpc.getServer(ApplicationHistoryProtocol.class, this,
                        address, conf, null, metricConfiguration.getTimelineMetricsServiceHandlerThreadCount());

        server.start();
        this.bindAddress =
                conf.updateConnectAddr(YarnConfiguration.TIMELINE_SERVICE_ADDRESS,
                        server.getListenerAddress());
        LOG.info("Instantiated ApplicationHistoryClientService at "
                + this.bindAddress);

        super.serviceStart();
    }

    @Override
    protected void serviceStop() throws Exception {
        if (server != null) {
            server.stop();
        }
        super.serviceStop();
    }

    public ApplicationHistoryProtocol getClientHandler() {
        return this;
    }


    public InetSocketAddress getBindAddress() {
        return this.bindAddress;
    }


    @Override
    public CancelDelegationTokenResponse cancelDelegationToken(
            CancelDelegationTokenRequest request) throws YarnException, IOException {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public GetApplicationAttemptReportResponse getApplicationAttemptReport(
            GetApplicationAttemptReportRequest request) throws YarnException,
            IOException {
        try {
            GetApplicationAttemptReportResponse response =
                    GetApplicationAttemptReportResponse.newInstance(history.getApplicationAttempt(request.getApplicationAttemptId()));
            return response;
        } catch (IOException e) {
            throw new ApplicationAttemptNotFoundException(e.getMessage());
        }
    }

    @Override
    public GetApplicationAttemptsResponse getApplicationAttempts(
            GetApplicationAttemptsRequest request) throws YarnException,
            IOException {
        GetApplicationAttemptsResponse response =
                GetApplicationAttemptsResponse
                        .newInstance(new ArrayList<ApplicationAttemptReport>(history
                                .getApplicationAttempts(request.getApplicationId()).values()));
        return response;
    }

    @Override
    public GetApplicationReportResponse getApplicationReport(
            GetApplicationReportRequest request) throws YarnException, IOException {
        try {
            ApplicationId applicationId = request.getApplicationId();
            GetApplicationReportResponse response =
                    GetApplicationReportResponse.newInstance(history
                            .getApplication(applicationId));
            return response;
        } catch (IOException e) {
            throw new ApplicationNotFoundException(e.getMessage());
        }
    }

    @Override
    public GetApplicationsResponse getApplications(
            GetApplicationsRequest request) throws YarnException, IOException {
        GetApplicationsResponse response =
                GetApplicationsResponse.newInstance(new ArrayList<ApplicationReport>(
                        history.getApplications(request.getLimit()).values()));
        return response;
    }

    @Override
    public GetContainerReportResponse getContainerReport(
            GetContainerReportRequest request) throws YarnException, IOException {
        try {
            GetContainerReportResponse response =
                    GetContainerReportResponse.newInstance(history.getContainer(request
                            .getContainerId()));
            return response;
        } catch (IOException e) {
            throw new ContainerNotFoundException(e.getMessage());
        }
    }

    @Override
    public GetContainersResponse getContainers(GetContainersRequest request)
            throws YarnException, IOException {
        GetContainersResponse response =
                GetContainersResponse.newInstance(new ArrayList<ContainerReport>(
                        history.getContainers(request.getApplicationAttemptId()).values()));
        return response;
    }

    @Override
    public GetDelegationTokenResponse getDelegationToken(
            GetDelegationTokenRequest request) throws YarnException, IOException {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public RenewDelegationTokenResponse renewDelegationToken(
            RenewDelegationTokenRequest request) throws YarnException, IOException {
        // TODO Auto-generated method stub
        return null;
    }


}
