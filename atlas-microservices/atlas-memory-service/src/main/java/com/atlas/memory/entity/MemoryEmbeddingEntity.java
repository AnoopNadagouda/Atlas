package com.atlas.memory.entity;

import jakarta.persistence.*;
import java.time.Instant;

@Entity
@Table(name = "atlas_memory_embeddings")
public class MemoryEmbeddingEntity {

    @Id
    private String memoryId;

    @Column(columnDefinition = "TEXT", nullable = false)
    private String vectorJson;

    private String modelName;
    private int dimension;
    private Instant createdAt;

    public MemoryEmbeddingEntity() {}

    public String getMemoryId() { return memoryId; }
    public void setMemoryId(String memoryId) { this.memoryId = memoryId; }

    public String getVectorJson() { return vectorJson; }
    public void setVectorJson(String vectorJson) { this.vectorJson = vectorJson; }

    public String getModelName() { return modelName; }
    public void setModelName(String modelName) { this.modelName = modelName; }

    public int getDimension() { return dimension; }
    public void setDimension(int dimension) { this.dimension = dimension; }

    public Instant getCreatedAt() { return createdAt; }
    public void setCreatedAt(Instant createdAt) { this.createdAt = createdAt; }
}
