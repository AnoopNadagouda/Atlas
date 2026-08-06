package com.atlas.domain.orchestrator;

import java.time.Instant;
import java.util.HashMap;
import java.util.Map;

public class ClusterState {
    private String clusterId;
    private int activeNodes;
    private int activeServices;
    private double overallCpuUsage;
    private double overallMemoryUsage;
    private String status; // HEALTHY, DEGRADED, CRITICAL
    private Map<String, String> serviceHealth = new HashMap<>();
    private Instant lastCheckTime;

    public ClusterState() {
    }

    public String getClusterId() { return clusterId; }
    public void setClusterId(String clusterId) { this.clusterId = clusterId; }

    public int getActiveNodes() { return activeNodes; }
    public void setActiveNodes(int activeNodes) { this.activeNodes = activeNodes; }

    public int getActiveServices() { return activeServices; }
    public void setActiveServices(int activeServices) { this.activeServices = activeServices; }

    public double getOverallCpuUsage() { return overallCpuUsage; }
    public void setOverallCpuUsage(double overallCpuUsage) { this.overallCpuUsage = overallCpuUsage; }

    public double getOverallMemoryUsage() { return overallMemoryUsage; }
    public void setOverallMemoryUsage(double overallMemoryUsage) { this.overallMemoryUsage = overallMemoryUsage; }

    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }

    public Map<String, String> getServiceHealth() { return serviceHealth; }
    public void setServiceHealth(Map<String, String> serviceHealth) { this.serviceHealth = serviceHealth; }

    public Instant getLastCheckTime() { return lastCheckTime; }
    public void setLastCheckTime(Instant lastCheckTime) { this.lastCheckTime = lastCheckTime; }
}
