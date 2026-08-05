package com.atlas.memory.entity;

import jakarta.persistence.*;
import java.time.Instant;

@Entity
@Table(name = "atlas_memory_relations", indexes = {
    @Index(name = "idx_rel_source", columnList = "sourceMemoryId"),
    @Index(name = "idx_rel_target", columnList = "targetMemoryId")
})
public class MemoryRelationEntity {

    @Id
    private String id;

    @Column(nullable = false)
    private String sourceMemoryId;

    @Column(nullable = false)
    private String targetMemoryId;

    @Column(nullable = false)
    private String relationType;

    private double weight;
    private Instant createdAt;

    public MemoryRelationEntity() {}

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
