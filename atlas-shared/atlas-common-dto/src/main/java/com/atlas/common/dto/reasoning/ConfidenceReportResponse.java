package com.atlas.common.dto.reasoning;

import java.util.HashMap;
import java.util.Map;

public class ConfidenceReportResponse {
    private String sessionId;
    private double overallConfidence;
    private double certaintyScore;
    private double riskScore;
    private String recommendation; // PROCEED, RECONSIDER, ABORT
    private Map<String, Double> factorScores = new HashMap<>();

    public ConfidenceReportResponse() {
    }

    public String getSessionId() { return sessionId; }
    public void setSessionId(String sessionId) { this.sessionId = sessionId; }

    public double getOverallConfidence() { return overallConfidence; }
    public void setOverallConfidence(double overallConfidence) { this.overallConfidence = overallConfidence; }

    public double getCertaintyScore() { return certaintyScore; }
    public void setCertaintyScore(double certaintyScore) { this.certaintyScore = certaintyScore; }

    public double getRiskScore() { return riskScore; }
    public void setRiskScore(double riskScore) { this.riskScore = riskScore; }

    public String getRecommendation() { return recommendation; }
    public void setRecommendation(String recommendation) { this.recommendation = recommendation; }

    public Map<String, Double> getFactorScores() { return factorScores; }
    public void setFactorScores(Map<String, Double> factorScores) { this.factorScores = factorScores; }
}
