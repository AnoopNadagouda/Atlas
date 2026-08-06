package com.atlas.kafka.events.orchestrator;

import java.time.Instant;

public class MissionCompletedEvent {
    private String eventId;
    private String missionId;
    private String tenantId;
    private double executionDurationMs;
    private Instant timestamp;

    public MissionCompletedEvent() {
    }

    public MissionCompletedEvent(String eventId, String missionId, String tenantId, double executionDurationMs) {
        this.eventId = eventId;
        this.missionId = missionId;
        this.tenantId = tenantId;
        this.executionDurationMs = executionDurationMs;
        this.timestamp = Instant.now();
    }

    public String getEventId() { return eventId; }
    public void setEventId(String eventId) { this.eventId = eventId; }

    public String getMissionId() { return missionId; }
    public void setMissionId(String missionId) { this.missionId = missionId; }

    public String getTenantId() { return tenantId; }
    public void setTenantId(String tenantId) { this.tenantId = tenantId; }

    public double getExecutionDurationMs() { return executionDurationMs; }
    public void setExecutionDurationMs(double executionDurationMs) { this.executionDurationMs = executionDurationMs; }

    public Instant getTimestamp() { return timestamp; }
    public void setTimestamp(Instant timestamp) { this.timestamp = timestamp; }
}
