package com.atlas.domain.orchestrator;

import java.time.Instant;

public class MissionExecution {
    private String id;
    private String missionId;
    private String tenantId;
    private String status;
    private double executionTimeMs;
    private Instant startTime;
    private Instant endTime;

    public MissionExecution() {
    }

    public String getId() { return id; }
    public void setId(String id) { this.id = id; }

    public String getMissionId() { return missionId; }
    public void setMissionId(String missionId) { this.missionId = missionId; }

    public String getTenantId() { return tenantId; }
    public void setTenantId(String tenantId) { this.tenantId = tenantId; }

    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }

    public double getExecutionTimeMs() { return executionTimeMs; }
    public void setExecutionTimeMs(double executionTimeMs) { this.executionTimeMs = executionTimeMs; }

    public Instant getStartTime() { return startTime; }
    public void setStartTime(Instant startTime) { this.startTime = startTime; }

    public Instant getEndTime() { return endTime; }
    public void setEndTime(Instant endTime) { this.endTime = endTime; }
}
