package com.atlas.common.dto.orchestrator;

import java.util.HashMap;
import java.util.Map;

public class PlatformStatisticsResponse {
    private long totalMissions;
    private long activeAgents;
    private long activeWorkflows;
    private long totalMemories;
    private long totalReasoningSessions;
    private double overallHealthScore;
    private Map<String, Long> requestsByService = new HashMap<>();

    public PlatformStatisticsResponse() {
    }

    public long getTotalMissions() { return totalMissions; }
    public void setTotalMissions(long totalMissions) { this.totalMissions = totalMissions; }

    public long getActiveAgents() { return activeAgents; }
    public void setActiveAgents(long activeAgents) { this.activeAgents = activeAgents; }

    public long getActiveWorkflows() { return activeWorkflows; }
    public void setActiveWorkflows(long activeWorkflows) { this.activeWorkflows = activeWorkflows; }

    public long getTotalMemories() { return totalMemories; }
    public void setTotalMemories(long totalMemories) { this.totalMemories = totalMemories; }

    public long getTotalReasoningSessions() { return totalReasoningSessions; }
    public void setTotalReasoningSessions(long totalReasoningSessions) { this.totalReasoningSessions = totalReasoningSessions; }

    public double getOverallHealthScore() { return overallHealthScore; }
    public void setOverallHealthScore(double overallHealthScore) { this.overallHealthScore = overallHealthScore; }

    public Map<String, Long> getRequestsByService() { return requestsByService; }
    public void setRequestsByService(Map<String, Long> requestsByService) { this.requestsByService = requestsByService; }
}
