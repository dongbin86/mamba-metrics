package mamba.applicationhistoryservice.records;

import org.apache.hadoop.classification.InterfaceAudience.Public;
import org.apache.hadoop.classification.InterfaceStability.Unstable;
import org.apache.hadoop.yarn.api.records.ContainerId;
import org.apache.hadoop.yarn.api.records.ContainerState;
import org.apache.hadoop.yarn.util.Records;

/**
 * Created by sanbing on 10/10/16.
 */
public abstract class ContainerFinishData {

  @Public
  @Unstable
  public static ContainerFinishData newInstance(ContainerId containerId,
      long finishTime, String diagnosticsInfo, int containerExitCode,
      ContainerState containerState) {
    ContainerFinishData containerFD =
        Records.newRecord(ContainerFinishData.class);
    containerFD.setContainerId(containerId);
    containerFD.setFinishTime(finishTime);
    containerFD.setDiagnosticsInfo(diagnosticsInfo);
    containerFD.setContainerExitStatus(containerExitCode);
    containerFD.setContainerState(containerState);
    return containerFD;
  }

  @Public
  @Unstable
  public abstract ContainerId getContainerId();

  @Public
  @Unstable
  public abstract void setContainerId(ContainerId containerId);

  @Public
  @Unstable
  public abstract long getFinishTime();

  @Public
  @Unstable
  public abstract void setFinishTime(long finishTime);

  @Public
  @Unstable
  public abstract String getDiagnosticsInfo();

  @Public
  @Unstable
  public abstract void setDiagnosticsInfo(String diagnosticsInfo);

  @Public
  @Unstable
  public abstract int getContainerExitStatus();

  @Public
  @Unstable
  public abstract void setContainerExitStatus(int containerExitStatus);

  @Public
  @Unstable
  public abstract ContainerState getContainerState();

  @Public
  @Unstable
  public abstract void setContainerState(ContainerState containerState);

}
