package com.atlas.domain.orchestrator;

import java.util.HashMap;
import java.util.Map;

public class PlatformStatistics {
    private long totalMissionsExecuted;
    private long activeAgentsCount;
    private long activeWorkflowsCount;
    private long totalMemoriesStored;
    private long totalReasoningSessions;
    private double platformUptimePercentage;
    private Map<String, Long> metricsByService = new HashMap<>();

    public PlatformStatistics() {
    }

    public long getTotalMissionsExecuted() { return totalMissionsExecuted; }
    public void setTotalMissionsExecuted(long totalMissionsExecuted) { this.totalMissionsExecuted = totalMissionsExecuted; }

    public long getActiveAgentsCount() { return activeAgentsCount; }
    public void setActiveAgentsCount(long activeAgentsCount) { this.activeAgentsCount = activeAgentsCount; }

    public long getActiveWorkflowsCount() { return activeWorkflowsCount; }
    public void setActiveWorkflowsCount(long activeWorkflowsCount) { this.activeWorkflowsCount = activeWorkflowsCount; }

    public long getTotalMemoriesStored() { return totalMemoriesStored; }
    public void setTotalMemoriesStored(long totalMemoriesStored) { this.totalMemoriesStored = totalMemoriesStored; }

    public long getTotalReasoningSessions() { return totalReasoningSessions; }
    public void setTotalReasoningSessions(long totalReasoningSessions) { this.totalReasoningSessions = totalReasoningSessions; }

    public double getPlatformUptimePercentage() { return platformUptimePercentage; }
    public void setPlatformUptimePercentage(double platformUptimePercentage) { this.platformUptimePercentage = platformUptimePercentage; }

    public Map<String, Long> getMetricsByService() { return metricsByService; }
    public void setMetricsByService(Map<String, Long> metricsByService) { this.metricsByService = metricsByService; }
}
