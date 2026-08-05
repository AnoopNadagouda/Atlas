package com.atlas.domain.memory;

import java.time.Instant;

/**
 * Domain model for dense vector embeddings associated with a memory.
 */
public class MemoryEmbedding {
    private String memoryId;
    private float[] vector;
    private String modelName;
    private int dimension;
    private Instant createdAt;

    public MemoryEmbedding() {
        this.createdAt = Instant.now();
    }

    public MemoryEmbedding(String memoryId, float[] vector, String modelName) {
        this();
        this.memoryId = memoryId;
        this.vector = vector;
        this.modelName = modelName;
        this.dimension = vector != null ? vector.length : 0;
    }

    public String getMemoryId() { return memoryId; }
    public void setMemoryId(String memoryId) { this.memoryId = memoryId; }

    public float[] getVector() { return vector; }
    public void setVector(float[] vector) {
        this.vector = vector;
        this.dimension = vector != null ? vector.length : 0;
    }

    public String getModelName() { return modelName; }
    public void setModelName(String modelName) { this.modelName = modelName; }

    public int getDimension() { return dimension; }
    public void setDimension(int dimension) { this.dimension = dimension; }

    public Instant getCreatedAt() { return createdAt; }
    public void setCreatedAt(Instant createdAt) { this.createdAt = createdAt; }
}
