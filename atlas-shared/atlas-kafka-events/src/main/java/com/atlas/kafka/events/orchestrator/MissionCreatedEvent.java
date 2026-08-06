package com.atlas.kafka.events.orchestrator;

import java.time.Instant;

public class MissionCreatedEvent {
    private String eventId;
    private String missionId;
    private String tenantId;
    private String title;
    private Instant timestamp;

    public MissionCreatedEvent() {
    }

    public MissionCreatedEvent(String eventId, String missionId, String tenantId, String title) {
        this.eventId = eventId;
        this.missionId = missionId;
        this.tenantId = tenantId;
        this.title = title;
        this.timestamp = Instant.now();
    }

    public String getEventId() { return eventId; }
    public void setEventId(String eventId) { this.eventId = eventId; }

    public String getMissionId() { return missionId; }
    public void setMissionId(String missionId) { this.missionId = missionId; }

    public String getTenantId() { return tenantId; }
    public void setTenantId(String tenantId) { this.tenantId = tenantId; }

    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }

    public Instant getTimestamp() { return timestamp; }
    public void setTimestamp(Instant timestamp) { this.timestamp = timestamp; }
}
