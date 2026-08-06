package com.atlas.reasoning.entity;

import jakarta.persistence.*;
import java.time.Instant;

@Entity
@Table(name = "atlas_thought_nodes", indexes = {
    @Index(name = "idx_tnode_session", columnList = "sessionId")
})
public class ThoughtNodeEntity {

    @Id
    private String id;

    @Column(nullable = false)
    private String sessionId;

    @Column(columnDefinition = "TEXT", nullable = false)
    private String thoughtContent;

    private String nodeType;
    private double score;
    private double depth;
    private Instant createdAt;

    public ThoughtNodeEntity() {}

    public String getId() { return id; }
    public void setId(String id) { this.id = id; }

    public String getSessionId() { return sessionId; }
    public void setSessionId(String sessionId) { this.sessionId = sessionId; }

    public String getThoughtContent() { return thoughtContent; }
    public void setThoughtContent(String thoughtContent) { this.thoughtContent = thoughtContent; }

    public String getNodeType() { return nodeType; }
    public void setNodeType(String nodeType) { this.nodeType = nodeType; }

    public double getScore() { return score; }
    public void setScore(double score) { this.score = score; }

    public double getDepth() { return depth; }
    public void setDepth(double depth) { this.depth = depth; }

    public Instant getCreatedAt() { return createdAt; }
    public void setCreatedAt(Instant createdAt) { this.createdAt = createdAt; }
}
