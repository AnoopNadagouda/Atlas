package com.atlas.domain.reasoning;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

public class DecisionRecord {
    private String id;
    private String sessionId;
    private String decisionPoint;
    private List<String> optionsEvaluated = new ArrayList<>();
    private String chosenOption;
    private String justification;
    private double costEstimate;
    private double riskEstimate;
    private double expectedReward;
    private double confidenceScore;
    private Instant timestamp;

    public DecisionRecord() {
    }

    public DecisionRecord(String id, String sessionId, String decisionPoint, List<String> optionsEvaluated, String chosenOption, String justification, double costEstimate, double riskEstimate, double expectedReward, double confidenceScore) {
        this.id = id;
        this.sessionId = sessionId;
        this.decisionPoint = decisionPoint;
        this.optionsEvaluated = optionsEvaluated;
        this.chosenOption = chosenOption;
        this.justification = justification;
        this.costEstimate = costEstimate;
        this.riskEstimate = riskEstimate;
        this.expectedReward = expectedReward;
        this.confidenceScore = confidenceScore;
        this.timestamp = Instant.now();
    }

    public String getId() { return id; }
    public void setId(String id) { this.id = id; }

    public String getSessionId() { return sessionId; }
    public void setSessionId(String sessionId) { this.sessionId = sessionId; }

    public String getDecisionPoint() { return decisionPoint; }
    public void setDecisionPoint(String decisionPoint) { this.decisionPoint = decisionPoint; }

    public List<String> getOptionsEvaluated() { return optionsEvaluated; }
    public void setOptionsEvaluated(List<String> optionsEvaluated) { this.optionsEvaluated = optionsEvaluated; }

    public String getChosenOption() { return chosenOption; }
    public void setChosenOption(String chosenOption) { this.chosenOption = chosenOption; }

    public String getJustification() { return justification; }
    public void setJustification(String justification) { this.justification = justification; }

    public double getCostEstimate() { return costEstimate; }
    public void setCostEstimate(double costEstimate) { this.costEstimate = costEstimate; }

    public double getRiskEstimate() { return riskEstimate; }
    public void setRiskEstimate(double riskEstimate) { this.riskEstimate = riskEstimate; }

    public double getExpectedReward() { return expectedReward; }
    public void setExpectedReward(double expectedReward) { this.expectedReward = expectedReward; }

    public double getConfidenceScore() { return confidenceScore; }
    public void setConfidenceScore(double confidenceScore) { this.confidenceScore = confidenceScore; }

    public Instant getTimestamp() { return timestamp; }
    public void setTimestamp(Instant timestamp) { this.timestamp = timestamp; }
}
