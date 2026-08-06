package com.atlas.domain.reasoning;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

public class ReasoningSession {
    private String id;
    private String tenantId;
    private String goalId;
    private String agentId;
    private ReasoningMode mode;
    private String status; // IN_PROGRESS, COMPLETED, FAILED, REPLANNING
    private double overallConfidence;
    private double riskScore;
    private List<ReasoningStep> steps = new ArrayList<>();
    private List<DecisionRecord> decisions = new ArrayList<>();
    private List<ReflectionRecord> reflections = new ArrayList<>();
    private Instant createdAt;
    private Instant updatedAt;

    public ReasoningSession() {
    }

    public ReasoningSession(String id, String tenantId, String goalId, String agentId, ReasoningMode mode, String status) {
        this.id = id;
        this.tenantId = tenantId;
        this.goalId = goalId;
        this.agentId = agentId;
        this.mode = mode;
        this.status = status;
        this.createdAt = Instant.now();
        this.updatedAt = Instant.now();
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

    public List<DecisionRecord> getDecisions() { return decisions; }
    public void setDecisions(List<DecisionRecord> decisions) { this.decisions = decisions; }

    public List<ReflectionRecord> getReflections() { return reflections; }
    public void setReflections(List<ReflectionRecord> reflections) { this.reflections = reflections; }

    public Instant getCreatedAt() { return createdAt; }
    public void setCreatedAt(Instant createdAt) { this.createdAt = createdAt; }

    public Instant getUpdatedAt() { return updatedAt; }
    public void setUpdatedAt(Instant updatedAt) { this.updatedAt = updatedAt; }
}
