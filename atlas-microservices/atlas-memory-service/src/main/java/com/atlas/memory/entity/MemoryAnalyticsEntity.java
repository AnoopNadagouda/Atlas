package com.atlas.memory.entity;

import jakarta.persistence.*;
import java.time.Instant;

@Entity
@Table(name = "atlas_memory_analytics")
public class MemoryAnalyticsEntity {

    @Id
    private String id;

    @Column(nullable = false)
    private String tenantId;

    private long totalMemories;
    private long activeMemories;
    private long consolidatedMemories;
    private long decayedMemories;
    private double averageImportance;

    @Column(columnDefinition = "TEXT")
    private String countByTypeJson;

    private Instant calculatedAt;

    public MemoryAnalyticsEntity() {}

    public String getId() { return id; }
    public void setId(String id) { this.id = id; }

    public String getTenantId() { return tenantId; }
    public void setTenantId(String tenantId) { this.tenantId = tenantId; }

    public long getTotalMemories() { return totalMemories; }
    public void setTotalMemories(long totalMemories) { this.totalMemories = totalMemories; }

    public long getActiveMemories() { return activeMemories; }
    public void setActiveMemories(long activeMemories) { this.activeMemories = activeMemories; }

    public long getConsolidatedMemories() { return consolidatedMemories; }
    public void setConsolidatedMemories(long consolidatedMemories) { this.consolidatedMemories = consolidatedMemories; }

    public long getDecayedMemories() { return decayedMemories; }
    public void setDecayedMemories(long decayedMemories) { this.decayedMemories = decayedMemories; }

    public double getAverageImportance() { return averageImportance; }
    public void setAverageImportance(double averageImportance) { this.averageImportance = averageImportance; }

    public String getCountByTypeJson() { return countByTypeJson; }
    public void setCountByTypeJson(String countByTypeJson) { this.countByTypeJson = countByTypeJson; }

    public Instant getCalculatedAt() { return calculatedAt; }
    public void setCalculatedAt(Instant calculatedAt) { this.calculatedAt = calculatedAt; }
}
