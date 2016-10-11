package mamba.applicationhistoryservice;

import mamba.applicationhistoryservice.records.ApplicationAttemptHistoryData;
import mamba.applicationhistoryservice.records.ApplicationHistoryData;
import mamba.applicationhistoryservice.records.ContainerHistoryData;
import org.apache.hadoop.yarn.api.records.ApplicationAttemptId;
import org.apache.hadoop.yarn.api.records.ApplicationId;
import org.apache.hadoop.yarn.api.records.ContainerId;

import java.io.IOException;
import java.util.Map;


/**
 * Created by sanbing on 10/10/16.
 */
public interface ApplicationHistoryReader {


    ApplicationHistoryData getApplication(ApplicationId appId) throws IOException;


    Map<ApplicationId, ApplicationHistoryData> getAllApplications()
            throws IOException;

    Map<ApplicationAttemptId, ApplicationAttemptHistoryData>
    getApplicationAttempts(ApplicationId appId) throws IOException;


    ApplicationAttemptHistoryData getApplicationAttempt(
            ApplicationAttemptId appAttemptId) throws IOException;


    ContainerHistoryData getContainer(ContainerId containerId) throws IOException;


    ContainerHistoryData getAMContainer(ApplicationAttemptId appAttemptId)
            throws IOException;


    Map<ContainerId, ContainerHistoryData> getContainers(
            ApplicationAttemptId appAttemptId) throws IOException;
}
