package com.atlas.common.dto.orchestrator;

import java.util.ArrayList;
import java.util.List;

public class ClusterOverviewResponse {
    private String clusterId;
    private int totalNodes;
    private int healthyServicesCount;
    private double cpuUtilization;
    private double memoryUtilization;
    private List<String> nodeNames = new ArrayList<>();

    public ClusterOverviewResponse() {
    }

    public String getClusterId() { return clusterId; }
    public void setClusterId(String clusterId) { this.clusterId = clusterId; }

    public int getTotalNodes() { return totalNodes; }
    public void setTotalNodes(int totalNodes) { this.totalNodes = totalNodes; }

    public int getHealthyServicesCount() { return healthyServicesCount; }
    public void setHealthyServicesCount(int healthyServicesCount) { this.healthyServicesCount = healthyServicesCount; }

    public double getCpuUtilization() { return cpuUtilization; }
    public void setCpuUtilization(double cpuUtilization) { this.cpuUtilization = cpuUtilization; }

    public double getMemoryUtilization() { return memoryUtilization; }
    public void setMemoryUtilization(double memoryUtilization) { this.memoryUtilization = memoryUtilization; }

    public List<String> getNodeNames() { return nodeNames; }
    public void setNodeNames(List<String> nodeNames) { this.nodeNames = nodeNames; }
}
