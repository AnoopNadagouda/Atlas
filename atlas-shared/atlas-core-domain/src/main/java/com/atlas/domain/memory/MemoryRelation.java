package com.atlas.domain.memory;

import java.time.Instant;

/**
 * Domain model for memory relationships in Atlas Knowledge Graph.
 */
public class MemoryRelation {
    private String id;
    private String sourceMemoryId;
    private String targetMemoryId;
    private String relationType;
    private double weight;
    private Instant createdAt;

    public MemoryRelation() {
        this.createdAt = Instant.now();
        this.weight = 1.0;
    }

    public MemoryRelation(String id, String sourceMemoryId, String targetMemoryId, String relationType, double weight) {
        this();
        this.id = id;
        this.sourceMemoryId = sourceMemoryId;
        this.targetMemoryId = targetMemoryId;
        this.relationType = relationType;
        this.weight = weight;
    }

    public String getId() { return id; }
    public void setId(String id) { this.id = id; }

    public String getSourceMemoryId() { return sourceMemoryId; }
    public void setSourceMemoryId(String sourceMemoryId) { this.sourceMemoryId = sourceMemoryId; }

    public String getTargetMemoryId() { return targetMemoryId; }
    public void setTargetMemoryId(String targetMemoryId) { this.targetMemoryId = targetMemoryId; }

    public String getRelationType() { return relationType; }
    public void setRelationType(String relationType) { this.relationType = relationType; }

    public double getWeight() { return weight; }
    public void setWeight(double weight) { this.weight = weight; }

    public Instant getCreatedAt() { return createdAt; }
    public void setCreatedAt(Instant createdAt) { this.createdAt = createdAt; }
}
