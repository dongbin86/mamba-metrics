package mamba.applicationhistoryservice.records;


import org.apache.hadoop.yarn.api.records.ApplicationAttemptId;
import org.apache.hadoop.yarn.api.records.ContainerId;
import org.apache.hadoop.yarn.util.Records;

/**
 * Created by sanbing on 10/10/16.
 */
public abstract class ApplicationAttemptStartData {


  public static ApplicationAttemptStartData newInstance(
      ApplicationAttemptId appAttemptId, String host, int rpcPort,
      ContainerId masterContainerId) {
    ApplicationAttemptStartData appAttemptSD =
        Records.newRecord(ApplicationAttemptStartData.class);
    appAttemptSD.setApplicationAttemptId(appAttemptId);
    appAttemptSD.setHost(host);
    appAttemptSD.setRPCPort(rpcPort);
    appAttemptSD.setMasterContainerId(masterContainerId);
    return appAttemptSD;
  }


  public abstract ApplicationAttemptId getApplicationAttemptId();


  public abstract void setApplicationAttemptId(
      ApplicationAttemptId applicationAttemptId);


  public abstract String getHost();


  public abstract void setHost(String host);


  public abstract int getRPCPort();


  public abstract void setRPCPort(int rpcPort);

  public abstract ContainerId getMasterContainerId();


  public abstract void setMasterContainerId(ContainerId masterContainerId);

}
