package com.atlas.memory.entity;

import jakarta.persistence.*;
import java.time.Instant;

@Entity
@Table(name = "atlas_conversations", indexes = {
    @Index(name = "idx_conv_tenant", columnList = "tenantId"),
    @Index(name = "idx_conv_agent", columnList = "agentId")
})
public class ConversationEntity {

    @Id
    private String id;

    @Column(nullable = false)
    private String tenantId;

    private String agentId;
    private String title;
    private int messageCount;
    private Instant createdAt;
    private Instant updatedAt;

    public ConversationEntity() {}

    public String getId() { return id; }
    public void setId(String id) { this.id = id; }

    public String getTenantId() { return tenantId; }
    public void setTenantId(String tenantId) { this.tenantId = tenantId; }

    public String getAgentId() { return agentId; }
    public void setAgentId(String agentId) { this.agentId = agentId; }

    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }

    public int getMessageCount() { return messageCount; }
    public void setMessageCount(int messageCount) { this.messageCount = messageCount; }

    public Instant getCreatedAt() { return createdAt; }
    public void setCreatedAt(Instant createdAt) { this.createdAt = createdAt; }

    public Instant getUpdatedAt() { return updatedAt; }
    public void setUpdatedAt(Instant updatedAt) { this.updatedAt = updatedAt; }
}
