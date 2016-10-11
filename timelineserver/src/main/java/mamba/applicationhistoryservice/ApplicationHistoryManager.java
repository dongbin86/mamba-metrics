package mamba.applicationhistoryservice;

import org.apache.hadoop.yarn.api.records.*;
import org.apache.hadoop.yarn.exceptions.YarnException;

import java.io.IOException;
import java.util.Map;

/**
 * Created by sanbing on 10/10/16.
 */
public interface ApplicationHistoryManager {



    ApplicationReport getApplication(ApplicationId appId) throws YarnException,
            IOException;


    Map<ApplicationId, ApplicationReport>
    getApplications(long appsNum) throws YarnException,
            IOException;


    Map<ApplicationAttemptId, ApplicationAttemptReport> getApplicationAttempts(
            ApplicationId appId) throws YarnException, IOException;


    ApplicationAttemptReport getApplicationAttempt(
            ApplicationAttemptId appAttemptId) throws YarnException, IOException;


    ContainerReport getContainer(ContainerId containerId) throws YarnException,
            IOException;


    ContainerReport getAMContainer(ApplicationAttemptId appAttemptId)
            throws YarnException, IOException;


    Map<ContainerId, ContainerReport> getContainers(
            ApplicationAttemptId appAttemptId) throws YarnException, IOException;

}
