package mamba.metricsservice.records;

import org.apache.hadoop.yarn.api.records.ApplicationId;
import org.apache.hadoop.yarn.api.records.FinalApplicationStatus;
import org.apache.hadoop.yarn.api.records.YarnApplicationState;

/**
 * Created by sanbing on 10/10/16.
 */
public class ApplicationHistoryData {

    private ApplicationId applicationId;

    private String applicationName;

    private String applicationType;

    private String user;

    private String queue;

    private long submitTime;

    private long startTime;

    private long finishTime;

    private String diagnosticsInfo;

    private FinalApplicationStatus finalApplicationStatus;

    private YarnApplicationState yarnApplicationState;

    public static ApplicationHistoryData newInstance(ApplicationId applicationId,
                                                     String applicationName, String applicationType, String queue,
                                                     String user, long submitTime, long startTime, long finishTime,
                                                     String diagnosticsInfo, FinalApplicationStatus finalApplicationStatus,
                                                     YarnApplicationState yarnApplicationState) {
        ApplicationHistoryData appHD = new ApplicationHistoryData();
        appHD.setApplicationId(applicationId);
        appHD.setApplicationName(applicationName);
        appHD.setApplicationType(applicationType);
        appHD.setQueue(queue);
        appHD.setUser(user);
        appHD.setSubmitTime(submitTime);
        appHD.setStartTime(startTime);
        appHD.setFinishTime(finishTime);
        appHD.setDiagnosticsInfo(diagnosticsInfo);
        appHD.setFinalApplicationStatus(finalApplicationStatus);
        appHD.setYarnApplicationState(yarnApplicationState);
        return appHD;
    }


    public ApplicationId getApplicationId() {
        return applicationId;
    }


    public void setApplicationId(ApplicationId applicationId) {
        this.applicationId = applicationId;
    }


    public String getApplicationName() {
        return applicationName;
    }


    public void setApplicationName(String applicationName) {
        this.applicationName = applicationName;
    }

    public String getApplicationType() {
        return applicationType;
    }

    public void setApplicationType(String applicationType) {
        this.applicationType = applicationType;
    }


    public String getUser() {
        return user;
    }


    public void setUser(String user) {
        this.user = user;
    }


    public String getQueue() {
        return queue;
    }


    public void setQueue(String queue) {
        this.queue = queue;
    }

    public long getSubmitTime() {
        return submitTime;
    }


    public void setSubmitTime(long submitTime) {
        this.submitTime = submitTime;
    }


    public long getStartTime() {
        return startTime;
    }


    public void setStartTime(long startTime) {
        this.startTime = startTime;
    }

    public long getFinishTime() {
        return finishTime;
    }


    public void setFinishTime(long finishTime) {
        this.finishTime = finishTime;
    }

    public String getDiagnosticsInfo() {
        return diagnosticsInfo;
    }


    public void setDiagnosticsInfo(String diagnosticsInfo) {
        this.diagnosticsInfo = diagnosticsInfo;
    }

    public FinalApplicationStatus getFinalApplicationStatus() {
        return finalApplicationStatus;
    }


    public void setFinalApplicationStatus(
            FinalApplicationStatus finalApplicationStatus) {
        this.finalApplicationStatus = finalApplicationStatus;
    }

    public YarnApplicationState getYarnApplicationState() {
        return this.yarnApplicationState;
    }


    public void
    setYarnApplicationState(YarnApplicationState yarnApplicationState) {
        this.yarnApplicationState = yarnApplicationState;
    }

}
