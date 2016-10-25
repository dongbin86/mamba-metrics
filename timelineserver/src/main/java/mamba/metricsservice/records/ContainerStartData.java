package mamba.metricsservice.records;

import org.apache.hadoop.yarn.api.records.ContainerId;
import org.apache.hadoop.yarn.api.records.NodeId;
import org.apache.hadoop.yarn.api.records.Priority;
import org.apache.hadoop.yarn.api.records.Resource;
import org.apache.hadoop.yarn.util.Records;


/**
 * Created by sanbing on 10/10/16.
 */
public abstract class ContainerStartData {


  public static ContainerStartData newInstance(ContainerId containerId,
      Resource allocatedResource, NodeId assignedNode, Priority priority,
      long startTime) {
    ContainerStartData containerSD =
        Records.newRecord(ContainerStartData.class);
    containerSD.setContainerId(containerId);
    containerSD.setAllocatedResource(allocatedResource);
    containerSD.setAssignedNode(assignedNode);
    containerSD.setPriority(priority);
    containerSD.setStartTime(startTime);
    return containerSD;
  }


  public abstract ContainerId getContainerId();


  public abstract void setContainerId(ContainerId containerId);

  public abstract Resource getAllocatedResource();


  public abstract void setAllocatedResource(Resource resource);


  public abstract NodeId getAssignedNode();


  public abstract void setAssignedNode(NodeId nodeId);


  public abstract Priority getPriority();


  public abstract void setPriority(Priority priority);


  public abstract long getStartTime();


  public abstract void setStartTime(long startTime);

}
