package com.atlas.reasoning.entity;

import com.atlas.domain.reasoning.ReasoningMode;
import jakarta.persistence.*;

import java.time.Instant;

@Entity
@Table(name = "reasoning_sessions")
public class ReasoningSessionEntity {

    @Id
    private String id;

    @Column(name = "tenant_id", nullable = false)
    private String tenantId;

    @Column(name = "goal_id")
    private String goalId;

    @Column(name = "agent_id")
    private String agentId;

    @Enumerated(EnumType.STRING)
    @Column(name = "mode", nullable = false)
    private ReasoningMode mode;

    @Column(name = "status", nullable = false)
    private String status;

    @Column(name = "overall_confidence")
    private double overallConfidence;

    @Column(name = "risk_score")
    private double riskScore;

    @Column(name = "created_at", nullable = false)
    private Instant createdAt;

    @Column(name = "updated_at", nullable = false)
    private Instant updatedAt;

    public ReasoningSessionEntity() {
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

    public Instant getCreatedAt() { return createdAt; }
    public void setCreatedAt(Instant createdAt) { this.createdAt = createdAt; }

    public Instant getUpdatedAt() { return updatedAt; }
    public void setUpdatedAt(Instant updatedAt) { this.updatedAt = updatedAt; }
}
