package mamba.applicationhistoryservice.records;

import org.apache.hadoop.yarn.api.records.ApplicationAttemptId;
import org.apache.hadoop.yarn.api.records.ContainerId;
import org.apache.hadoop.yarn.api.records.FinalApplicationStatus;
import org.apache.hadoop.yarn.api.records.YarnApplicationAttemptState;

/**
 * Created by sanbing on 10/10/16.
 */
public class ApplicationAttemptHistoryData {

    private ApplicationAttemptId applicationAttemptId;

    private String host;

    private int rpcPort;

    private String trackingURL;

    private String diagnosticsInfo;

    private FinalApplicationStatus finalApplicationStatus;

    private ContainerId masterContainerId;

    private YarnApplicationAttemptState yarnApplicationAttemptState;

    public static ApplicationAttemptHistoryData newInstance(
            ApplicationAttemptId appAttemptId, String host, int rpcPort,
            ContainerId masterContainerId, String diagnosticsInfo,
            String trackingURL, FinalApplicationStatus finalApplicationStatus,
            YarnApplicationAttemptState yarnApplicationAttemptState) {
        ApplicationAttemptHistoryData appAttemptHD =
                new ApplicationAttemptHistoryData();
        appAttemptHD.setApplicationAttemptId(appAttemptId);
        appAttemptHD.setHost(host);
        appAttemptHD.setRPCPort(rpcPort);
        appAttemptHD.setMasterContainerId(masterContainerId);
        appAttemptHD.setDiagnosticsInfo(diagnosticsInfo);
        appAttemptHD.setTrackingURL(trackingURL);
        appAttemptHD.setFinalApplicationStatus(finalApplicationStatus);
        appAttemptHD.setYarnApplicationAttemptState(yarnApplicationAttemptState);
        return appAttemptHD;
    }


    public ApplicationAttemptId getApplicationAttemptId() {
        return applicationAttemptId;
    }


    public void
    setApplicationAttemptId(ApplicationAttemptId applicationAttemptId) {
        this.applicationAttemptId = applicationAttemptId;
    }


    public String getHost() {
        return host;
    }


    public void setHost(String host) {
        this.host = host;
    }


    public int getRPCPort() {
        return rpcPort;
    }


    public void setRPCPort(int rpcPort) {
        this.rpcPort = rpcPort;
    }

    public String getTrackingURL() {
        return trackingURL;
    }

    public void setTrackingURL(String trackingURL) {
        this.trackingURL = trackingURL;
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


    public ContainerId getMasterContainerId() {
        return masterContainerId;
    }


    public void setMasterContainerId(ContainerId masterContainerId) {
        this.masterContainerId = masterContainerId;
    }

    public YarnApplicationAttemptState getYarnApplicationAttemptState() {
        return yarnApplicationAttemptState;
    }


    public void setYarnApplicationAttemptState(
            YarnApplicationAttemptState yarnApplicationAttemptState) {
        this.yarnApplicationAttemptState = yarnApplicationAttemptState;
    }

}
