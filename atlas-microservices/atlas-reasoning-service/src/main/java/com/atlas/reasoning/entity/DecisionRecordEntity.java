package com.atlas.reasoning.entity;

import jakarta.persistence.*;

import java.time.Instant;

@Entity
@Table(name = "decision_records")
public class DecisionRecordEntity {

    @Id
    private String id;

    @Column(name = "session_id", nullable = false)
    private String sessionId;

    @Column(name = "decision_point")
    private String decisionPoint;

    @Column(name = "options_evaluated", length = 2048)
    private String optionsEvaluatedJson;

    @Column(name = "chosen_option")
    private String chosenOption;

    @Column(name = "justification", length = 2048)
    private String justification;

    @Column(name = "cost_estimate")
    private double costEstimate;

    @Column(name = "risk_estimate")
    private double riskEstimate;

    @Column(name = "expected_reward")
    private double expectedReward;

    @Column(name = "confidence_score")
    private double confidenceScore;

    @Column(name = "timestamp", nullable = false)
    private Instant timestamp;

    public DecisionRecordEntity() {
    }

    public String getId() { return id; }
    public void setId(String id) { this.id = id; }

    public String getSessionId() { return sessionId; }
    public void setSessionId(String sessionId) { this.sessionId = sessionId; }

    public String getDecisionPoint() { return decisionPoint; }
    public void setDecisionPoint(String decisionPoint) { this.decisionPoint = decisionPoint; }

    public String getOptionsEvaluatedJson() { return optionsEvaluatedJson; }
    public void setOptionsEvaluatedJson(String optionsEvaluatedJson) { this.optionsEvaluatedJson = optionsEvaluatedJson; }

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
