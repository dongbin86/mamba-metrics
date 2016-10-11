package mamba.applicationhistoryservice.records;

import org.apache.hadoop.yarn.api.records.ApplicationAttemptId;
import org.apache.hadoop.yarn.api.records.FinalApplicationStatus;
import org.apache.hadoop.yarn.api.records.YarnApplicationAttemptState;
import org.apache.hadoop.yarn.util.Records;

/**
 * Created by sanbing on 10/10/16.
 */
public abstract class ApplicationAttemptFinishData {


  public static ApplicationAttemptFinishData newInstance(
      ApplicationAttemptId appAttemptId, String diagnosticsInfo,
      String trackingURL, FinalApplicationStatus finalApplicationStatus,
      YarnApplicationAttemptState yarnApplicationAttemptState) {
    ApplicationAttemptFinishData appAttemptFD =
        Records.newRecord(ApplicationAttemptFinishData.class);
    appAttemptFD.setApplicationAttemptId(appAttemptId);
    appAttemptFD.setDiagnosticsInfo(diagnosticsInfo);
    appAttemptFD.setTrackingURL(trackingURL);
    appAttemptFD.setFinalApplicationStatus(finalApplicationStatus);
    appAttemptFD.setYarnApplicationAttemptState(yarnApplicationAttemptState);
    return appAttemptFD;
  }


  public abstract ApplicationAttemptId getApplicationAttemptId();


  public abstract void setApplicationAttemptId(
      ApplicationAttemptId applicationAttemptId);


  public abstract String getTrackingURL();


  public abstract void setTrackingURL(String trackingURL);


  public abstract String getDiagnosticsInfo();


  public abstract void setDiagnosticsInfo(String diagnosticsInfo);


  public abstract FinalApplicationStatus getFinalApplicationStatus();

  public abstract void setFinalApplicationStatus(
      FinalApplicationStatus finalApplicationStatus);


  public abstract YarnApplicationAttemptState getYarnApplicationAttemptState();


  public abstract void setYarnApplicationAttemptState(
      YarnApplicationAttemptState yarnApplicationAttemptState);

}
