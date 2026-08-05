package com.atlas.domain.memory;

import java.time.Instant;
import java.util.HashMap;
import java.util.Map;

/**
 * Domain model representing a core memory entity in Atlas.
 */
public class Memory {
    private String id;
    private String tenantId;
    private String agentId;
    private String conversationId;
    private String workflowId;
    private String key;
    private String content;
    private MemoryType type;
    private MemoryState state;
    private double importanceScore;
    private double decayFactor;
    private int accessCount;
    private Instant createdAt;
    private Instant updatedAt;
    private Instant lastAccessedAt;
    private Instant expiresAt;
    private Map<String, Object> metadata;

    public Memory() {
        this.metadata = new HashMap<>();
        this.createdAt = Instant.now();
        this.updatedAt = Instant.now();
        this.lastAccessedAt = Instant.now();
        this.importanceScore = 0.5;
        this.decayFactor = 1.0;
        this.accessCount = 0;
        this.state = MemoryState.ACTIVE;
        this.tenantId = "default-tenant";
    }

    public Memory(String id, String agentId, String key, String content, MemoryType type) {
        this();
        this.id = id;
        this.agentId = agentId;
        this.key = key;
        this.content = content;
        this.type = type;
    }

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

    public Map<String, Object> getMetadata() { return metadata; }
    public void setMetadata(Map<String, Object> metadata) { this.metadata = metadata; }
}
