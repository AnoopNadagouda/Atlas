package com.atlas.kafka.events.orchestrator;

import java.time.Instant;

public class MissionFailedEvent {
    private String eventId;
    private String missionId;
    private String tenantId;
    private String failureReason;
    private Instant timestamp;

    public MissionFailedEvent() {
    }

    public MissionFailedEvent(String eventId, String missionId, String tenantId, String failureReason) {
        this.eventId = eventId;
        this.missionId = missionId;
        this.tenantId = tenantId;
        this.failureReason = failureReason;
        this.timestamp = Instant.now();
    }

    public String getEventId() { return eventId; }
    public void setEventId(String eventId) { this.eventId = eventId; }

    public String getMissionId() { return missionId; }
    public void setMissionId(String missionId) { this.missionId = missionId; }

    public String getTenantId() { return tenantId; }
    public void setTenantId(String tenantId) { this.tenantId = tenantId; }

    public String getFailureReason() { return failureReason; }
    public void setFailureReason(String failureReason) { this.failureReason = failureReason; }

    public Instant getTimestamp() { return timestamp; }
    public void setTimestamp(Instant timestamp) { this.timestamp = timestamp; }
}
