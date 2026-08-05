package com.atlas.memory.entity;

import jakarta.persistence.*;
import java.time.Instant;

@Entity
@Table(name = "atlas_memory_snapshots")
public class MemorySnapshotEntity {

    @Id
    private String id;

    @Column(nullable = false)
    private String tenantId;

    private String name;
    private int memoryCount;

    @Column(columnDefinition = "TEXT")
    private String snapshotDataJson;

    private Instant createdAt;

    public MemorySnapshotEntity() {}

    public String getId() { return id; }
    public void setId(String id) { this.id = id; }

    public String getTenantId() { return tenantId; }
    public void setTenantId(String tenantId) { this.tenantId = tenantId; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public int getMemoryCount() { return memoryCount; }
    public void setMemoryCount(int memoryCount) { this.memoryCount = memoryCount; }

    public String getSnapshotDataJson() { return snapshotDataJson; }
    public void setSnapshotDataJson(String snapshotDataJson) { this.snapshotDataJson = snapshotDataJson; }

    public Instant getCreatedAt() { return createdAt; }
    public void setCreatedAt(Instant createdAt) { this.createdAt = createdAt; }
}
