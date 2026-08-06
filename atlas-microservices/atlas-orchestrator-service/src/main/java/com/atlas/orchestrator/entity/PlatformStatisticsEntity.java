package com.atlas.orchestrator.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import java.time.Instant;

@Entity
@Table(name = "platform_statistics")
public class PlatformStatisticsEntity {

    @Id
    private String id;
    private String tenantId;
    private long totalMissionsExecuted;
    private long activeAgentsCount;
    private long activeWorkflowsCount;
    private long totalMemoriesStored;
    private long totalReasoningSessions;
    private double platformUptimePercentage;
    private Instant updatedAt;

    public PlatformStatisticsEntity() {
    }

    public String getId() { return id; }
    public void setId(String id) { this.id = id; }

    public String getTenantId() { return tenantId; }
    public void setTenantId(String tenantId) { this.tenantId = tenantId; }

    public long getTotalMissionsExecuted() { return totalMissionsExecuted; }
    public void setTotalMissionsExecuted(long totalMissionsExecuted) { this.totalMissionsExecuted = totalMissionsExecuted; }

    public long getActiveAgentsCount() { return activeAgentsCount; }
    public void setActiveAgentsCount(long activeAgentsCount) { this.activeAgentsCount = activeAgentsCount; }

    public long getActiveWorkflowsCount() { return activeWorkflowsCount; }
    public void setActiveWorkflowsCount(long activeWorkflowsCount) { this.activeWorkflowsCount = activeWorkflowsCount; }

    public long getTotalMemoriesStored() { return totalMemoriesStored; }
    public void setTotalMemoriesStored(long totalMemoriesStored) { this.totalMemoriesStored = totalMemoriesStored; }

    public long getTotalReasoningSessions() { return totalReasoningSessions; }
    public void setTotalReasoningSessions(long totalReasoningSessions) { this.totalReasoningSessions = totalReasoningSessions; }

    public double getPlatformUptimePercentage() { return platformUptimePercentage; }
    public void setPlatformUptimePercentage(double platformUptimePercentage) { this.platformUptimePercentage = platformUptimePercentage; }

    public Instant getUpdatedAt() { return updatedAt; }
    public void setUpdatedAt(Instant updatedAt) { this.updatedAt = updatedAt; }
}
