package com.atlas.kafka.events.orchestrator;

import java.time.Instant;

public class MissionResumedEvent {
    private String eventId;
    private String missionId;
    private String tenantId;
    private Instant timestamp;

    public MissionResumedEvent() {
    }

    public MissionResumedEvent(String eventId, String missionId, String tenantId) {
        this.eventId = eventId;
        this.missionId = missionId;
        this.tenantId = tenantId;
        this.timestamp = Instant.now();
    }

    public String getEventId() { return eventId; }
    public void setEventId(String eventId) { this.eventId = eventId; }

    public String getMissionId() { return missionId; }
    public void setMissionId(String missionId) { this.missionId = missionId; }

    public String getTenantId() { return tenantId; }
    public void setTenantId(String tenantId) { this.tenantId = tenantId; }

    public Instant getTimestamp() { return timestamp; }
    public void setTimestamp(Instant timestamp) { this.timestamp = timestamp; }
}
