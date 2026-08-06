package com.atlas.common.dto.reasoning;

import com.atlas.domain.reasoning.ReasoningMode;
import com.atlas.domain.reasoning.ReasoningStep;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

public class ReasoningResponse {
    private String id;
    private String tenantId;
    private String goalId;
    private String agentId;
    private ReasoningMode mode;
    private String status;
    private double overallConfidence;
    private double riskScore;
    private List<ReasoningStep> steps = new ArrayList<>();
    private Instant createdAt;

    public ReasoningResponse() {
    }

    public String getId() { return id; }
    public void setId(String id) { this.id = id; }

    public String getTenantId() { return tenantId; }
    public void setTenantId(String tenantId) { this.tenantId = tenantId; }

    public String getGoalId() { return goalId; }
    public void setGoalId(String goalId) { this.goalId = goalId; }

    public String getAgentId() { return agentId; }
    public void setAgentId(String agentId) { this.agentId = agentId; }

    public ReasoningMode getMode() { return mode; }
    public void setMode(ReasoningMode mode) { this.mode = mode; }

    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }

    public double getOverallConfidence() { return overallConfidence; }
    public void setOverallConfidence(double overallConfidence) { this.overallConfidence = overallConfidence; }

    public double getRiskScore() { return riskScore; }
    public void setRiskScore(double riskScore) { this.riskScore = riskScore; }

    public List<ReasoningStep> getSteps() { return steps; }
    public void setSteps(List<ReasoningStep> steps) { this.steps = steps; }

    public Instant getCreatedAt() { return createdAt; }
    public void setCreatedAt(Instant createdAt) { this.createdAt = createdAt; }
}
