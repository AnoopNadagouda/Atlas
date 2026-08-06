package com.atlas.reasoning.entity;

import jakarta.persistence.*;
import java.time.Instant;

@Entity
@Table(name = "atlas_reasoning_goals", indexes = {
    @Index(name = "idx_goal_tenant", columnList = "tenantId"),
    @Index(name = "idx_goal_agent", columnList = "agentId")
})
public class GoalEntity {

    @Id
    private String id;

    @Column(nullable = false)
    private String tenantId;

    private String agentId;

    @Column(nullable = false)
    private String title;

    @Column(columnDefinition = "TEXT")
    private String description;

    private String status;
    private int priority;
    private double successProbability;
    private Instant createdAt;
    private Instant completedAt;

    public GoalEntity() {}

    public String getId() { return id; }
    public void setId(String id) { this.id = id; }

    public String getTenantId() { return tenantId; }
    public void setTenantId(String tenantId) { this.tenantId = tenantId; }

    public String getAgentId() { return agentId; }
    public void setAgentId(String agentId) { this.agentId = agentId; }

    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }

    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }

    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }

    public int getPriority() { return priority; }
    public void setPriority(int priority) { this.priority = priority; }

    public double getSuccessProbability() { return successProbability; }
    public void setSuccessProbability(double successProbability) { this.successProbability = successProbability; }

    public Instant getCreatedAt() { return createdAt; }
    public void setCreatedAt(Instant createdAt) { this.createdAt = createdAt; }

    public Instant getCompletedAt() { return completedAt; }
    public void setCompletedAt(Instant completedAt) { this.completedAt = completedAt; }
}
