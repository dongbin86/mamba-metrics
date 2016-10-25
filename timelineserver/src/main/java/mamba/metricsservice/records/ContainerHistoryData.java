package mamba.metricsservice.records;

import org.apache.hadoop.yarn.api.records.*;

/**
 * Created by sanbing on 10/10/16.
 */
public class ContainerHistoryData {

    private ContainerId containerId;

    private Resource allocatedResource;

    private NodeId assignedNode;

    private Priority priority;

    private long startTime;

    private long finishTime;

    private String diagnosticsInfo;

    private int containerExitStatus;

    private ContainerState containerState;


    public static ContainerHistoryData newInstance(ContainerId containerId,
                                                   Resource allocatedResource, NodeId assignedNode, Priority priority,
                                                   long startTime, long finishTime, String diagnosticsInfo,
                                                   int containerExitCode, ContainerState containerState) {
        ContainerHistoryData containerHD = new ContainerHistoryData();
        containerHD.setContainerId(containerId);
        containerHD.setAllocatedResource(allocatedResource);
        containerHD.setAssignedNode(assignedNode);
        containerHD.setPriority(priority);
        containerHD.setStartTime(startTime);
        containerHD.setFinishTime(finishTime);
        containerHD.setDiagnosticsInfo(diagnosticsInfo);
        containerHD.setContainerExitStatus(containerExitCode);
        containerHD.setContainerState(containerState);
        return containerHD;
    }


    public ContainerId getContainerId() {
        return containerId;
    }


    public void setContainerId(ContainerId containerId) {
        this.containerId = containerId;
    }


    public Resource getAllocatedResource() {
        return allocatedResource;
    }


    public void setAllocatedResource(Resource resource) {
        this.allocatedResource = resource;
    }


    public NodeId getAssignedNode() {
        return assignedNode;
    }


    public void setAssignedNode(NodeId nodeId) {
        this.assignedNode = nodeId;
    }


    public Priority getPriority() {
        return priority;
    }

    public void setPriority(Priority priority) {
        this.priority = priority;
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


    public int getContainerExitStatus() {
        return containerExitStatus;
    }


    public void setContainerExitStatus(int containerExitStatus) {
        this.containerExitStatus = containerExitStatus;
    }

    public ContainerState getContainerState() {
        return containerState;
    }


    public void setContainerState(ContainerState containerState) {
        this.containerState = containerState;
    }

    
}
