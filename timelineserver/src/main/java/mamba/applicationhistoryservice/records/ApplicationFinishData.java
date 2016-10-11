package mamba.applicationhistoryservice.records;


import org.apache.hadoop.yarn.api.records.ApplicationId;
import org.apache.hadoop.yarn.api.records.FinalApplicationStatus;
import org.apache.hadoop.yarn.api.records.YarnApplicationState;
import org.apache.hadoop.yarn.util.Records;

/**
 * Created by sanbing on 10/10/16.
 */
public abstract class ApplicationFinishData {


  public static ApplicationFinishData newInstance(ApplicationId applicationId,
      long finishTime, String diagnosticsInfo,
      FinalApplicationStatus finalApplicationStatus,
      YarnApplicationState yarnApplicationState) {
    ApplicationFinishData appFD =
        Records.newRecord(ApplicationFinishData.class);
    appFD.setApplicationId(applicationId);
    appFD.setFinishTime(finishTime);
    appFD.setDiagnosticsInfo(diagnosticsInfo);
    appFD.setFinalApplicationStatus(finalApplicationStatus);
    appFD.setYarnApplicationState(yarnApplicationState);
    return appFD;
  }


  public abstract ApplicationId getApplicationId();


  public abstract void setApplicationId(ApplicationId applicationId);

  public abstract long getFinishTime();


  public abstract void setFinishTime(long finishTime);

  public abstract String getDiagnosticsInfo();


  public abstract void setDiagnosticsInfo(String diagnosticsInfo);


  public abstract FinalApplicationStatus getFinalApplicationStatus();


  public abstract void setFinalApplicationStatus(
      FinalApplicationStatus finalApplicationStatus);


  public abstract YarnApplicationState getYarnApplicationState();


  public abstract void setYarnApplicationState(
      YarnApplicationState yarnApplicationState);

}
