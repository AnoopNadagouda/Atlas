package com.atlas.reasoning.entity;

import jakarta.persistence.*;
import java.time.Instant;

@Entity
@Table(name = "atlas_reasoning_traces", indexes = {
    @Index(name = "idx_trace_session", columnList = "sessionId"),
    @Index(name = "idx_trace_tenant", columnList = "tenantId")
})
public class ReasoningTraceEntity {

    @Id
    private String id;

    @Column(nullable = false)
    private String sessionId;

    @Column(nullable = false)
    private String tenantId;

    private String agentId;

    @Column(columnDefinition = "TEXT")
    private String stepsJson;

    @Column(columnDefinition = "TEXT")
    private String thoughtsJson;

    private double overallConfidence;
    private Instant createdAt;

    public ReasoningTraceEntity() {}

    public String getId() { return id; }
    public void setId(String id) { this.id = id; }

    public String getSessionId() { return sessionId; }
    public void setSessionId(String sessionId) { this.sessionId = sessionId; }

    public String getTenantId() { return tenantId; }
    public void setTenantId(String tenantId) { this.tenantId = tenantId; }

    public String getAgentId() { return agentId; }
    public void setAgentId(String agentId) { this.agentId = agentId; }

    public String getStepsJson() { return stepsJson; }
    public void setStepsJson(String stepsJson) { this.stepsJson = stepsJson; }

    public String getThoughtsJson() { return thoughtsJson; }
    public void setThoughtsJson(String thoughtsJson) { this.thoughtsJson = thoughtsJson; }

    public double getOverallConfidence() { return overallConfidence; }
    public void setOverallConfidence(double overallConfidence) { this.overallConfidence = overallConfidence; }

    public Instant getCreatedAt() { return createdAt; }
    public void setCreatedAt(Instant createdAt) { this.createdAt = createdAt; }
}
