package com.atlas.common.dto.reasoning;

import java.util.HashMap;
import java.util.Map;

public class ReasoningAnalyticsResponse {
    private long totalSessions;
    private long completedSessions;
    private long failedSessions;
    private double averageConfidence;
    private double averageCorrectness;
    private long hallucinationsDetected;
    private Map<String, Long> countByMode = new HashMap<>();

    public ReasoningAnalyticsResponse() {
    }

    public long getTotalSessions() { return totalSessions; }
    public void setTotalSessions(long totalSessions) { this.totalSessions = totalSessions; }

    public long getCompletedSessions() { return completedSessions; }
    public void setCompletedSessions(long completedSessions) { this.completedSessions = completedSessions; }

    public long getFailedSessions() { return failedSessions; }
    public void setFailedSessions(long failedSessions) { this.failedSessions = failedSessions; }

    public double getAverageConfidence() { return averageConfidence; }
    public void setAverageConfidence(double averageConfidence) { this.averageConfidence = averageConfidence; }

    public double getAverageCorrectness() { return averageCorrectness; }
    public void setAverageCorrectness(double averageCorrectness) { this.averageCorrectness = averageCorrectness; }

    public long getHallucinationsDetected() { return hallucinationsDetected; }
    public void setHallucinationsDetected(long hallucinationsDetected) { this.hallucinationsDetected = hallucinationsDetected; }

    public Map<String, Long> getCountByMode() { return countByMode; }
    public void setCountByMode(Map<String, Long> countByMode) { this.countByMode = countByMode; }
}
