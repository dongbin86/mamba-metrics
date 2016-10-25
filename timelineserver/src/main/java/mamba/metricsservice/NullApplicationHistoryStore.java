package mamba.metricsservice;

import mamba.metricsservice.records.*;
import org.apache.hadoop.service.AbstractService;
import org.apache.hadoop.yarn.api.records.ApplicationAttemptId;
import org.apache.hadoop.yarn.api.records.ApplicationId;
import org.apache.hadoop.yarn.api.records.ContainerId;

import java.io.IOException;
import java.util.Collections;
import java.util.Map;

/**
 * Created by sanbing on 10/10/16.
 */
public class NullApplicationHistoryStore extends AbstractService implements
        ApplicationHistoryStore {


    public NullApplicationHistoryStore() {
        super(NullApplicationHistoryStore.class.getName());
    }

    @Override
    public void applicationStarted(ApplicationStartData appStart)
            throws IOException {
    }

    @Override
    public void applicationFinished(ApplicationFinishData appFinish)
            throws IOException {
    }

    @Override
    public void applicationAttemptStarted(
            ApplicationAttemptStartData appAttemptStart) throws IOException {
    }

    @Override
    public void applicationAttemptFinished(
            ApplicationAttemptFinishData appAttemptFinish) throws IOException {
    }

    @Override
    public void containerStarted(ContainerStartData containerStart)
            throws IOException {
    }

    @Override
    public void containerFinished(ContainerFinishData containerFinish)
            throws IOException {
    }

    @Override
    public ApplicationHistoryData getApplication(ApplicationId appId)
            throws IOException {
        return null;
    }

    @Override
    public Map<ApplicationId, ApplicationHistoryData> getAllApplications()
            throws IOException {
        return Collections.emptyMap();
    }

    @Override
    public Map<ApplicationAttemptId, ApplicationAttemptHistoryData>
    getApplicationAttempts(ApplicationId appId) throws IOException {
        return Collections.emptyMap();
    }

    @Override
    public ApplicationAttemptHistoryData getApplicationAttempt(
            ApplicationAttemptId appAttemptId) throws IOException {
        return null;
    }

    @Override
    public ContainerHistoryData getContainer(ContainerId containerId)
            throws IOException {
        return null;
    }

    @Override
    public ContainerHistoryData getAMContainer(ApplicationAttemptId appAttemptId)
            throws IOException {
        return null;
    }

    @Override
    public Map<ContainerId, ContainerHistoryData> getContainers(
            ApplicationAttemptId appAttemptId) throws IOException {
        return Collections.emptyMap();
    }

}
