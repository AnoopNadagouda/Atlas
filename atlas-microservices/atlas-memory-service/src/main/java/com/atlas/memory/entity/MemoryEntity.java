package com.atlas.memory.entity;

import com.atlas.domain.memory.MemoryState;
import com.atlas.domain.memory.MemoryType;
import jakarta.persistence.*;
import java.time.Instant;

@Entity
@Table(name = "atlas_memories", indexes = {
    @Index(name = "idx_mem_tenant", columnList = "tenantId"),
    @Index(name = "idx_mem_agent", columnList = "agentId"),
    @Index(name = "idx_mem_conv", columnList = "conversationId"),
    @Index(name = "idx_mem_wf", columnList = "workflowId"),
    @Index(name = "idx_mem_type", columnList = "type"),
    @Index(name = "idx_mem_state", columnList = "state")
})
public class MemoryEntity {

    @Id
    private String id;

    @Column(nullable = false)
    private String tenantId;

    private String agentId;
    private String conversationId;
    private String workflowId;

    @Column(name = "memory_key", nullable = false)
    private String key;

    @Column(columnDefinition = "TEXT", nullable = false)
    private String content;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private MemoryType type;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private MemoryState state;

    private double importanceScore;
    private double decayFactor;
    private int accessCount;

    @Column(nullable = false)
    private Instant createdAt;

    @Column(nullable = false)
    private Instant updatedAt;

    @Column(nullable = false)
    private Instant lastAccessedAt;

    private Instant expiresAt;

    @Column(columnDefinition = "TEXT")
    private String metadataJson;

    public MemoryEntity() {}

    public String getId() { return id; }
    public void setId(String id) { this.id = id; }

    public String getTenantId() { return tenantId; }
    public void setTenantId(String tenantId) { this.tenantId = tenantId; }

    public String getAgentId() { return agentId; }
    public void setAgentId(String agentId) { this.agentId = agentId; }

    public String getConversationId() { return conversationId; }
    public void setConversationId(String conversationId) { this.conversationId = conversationId; }

    public String getWorkflowId() { return workflowId; }
    public void setWorkflowId(String workflowId) { this.workflowId = workflowId; }

    public String getKey() { return key; }
    public void setKey(String key) { this.key = key; }

    public String getContent() { return content; }
    public void setContent(String content) { this.content = content; }

    public MemoryType getType() { return type; }
    public void setType(MemoryType type) { this.type = type; }

    public MemoryState getState() { return state; }
    public void setState(MemoryState state) { this.state = state; }

    public double getImportanceScore() { return importanceScore; }
    public void setImportanceScore(double importanceScore) { this.importanceScore = importanceScore; }

    public double getDecayFactor() { return decayFactor; }
    public void setDecayFactor(double decayFactor) { this.decayFactor = decayFactor; }

    public int getAccessCount() { return accessCount; }
    public void setAccessCount(int accessCount) { this.accessCount = accessCount; }

    public Instant getCreatedAt() { return createdAt; }
    public void setCreatedAt(Instant createdAt) { this.createdAt = createdAt; }

    public Instant getUpdatedAt() { return updatedAt; }
    public void setUpdatedAt(Instant updatedAt) { this.updatedAt = updatedAt; }

    public Instant getLastAccessedAt() { return lastAccessedAt; }
    public void setLastAccessedAt(Instant lastAccessedAt) { this.lastAccessedAt = lastAccessedAt; }

    public Instant getExpiresAt() { return expiresAt; }
    public void setExpiresAt(Instant expiresAt) { this.expiresAt = expiresAt; }

    public String getMetadataJson() { return metadataJson; }
    public void setMetadataJson(String metadataJson) { this.metadataJson = metadataJson; }
}
