package com.atlas.domain.orchestrator;

import java.time.Instant;

public class ExecutionTimeline {
    private String eventId;
    private String missionId;
    private String eventType;
    private String eventDetails;
    private Instant timestamp;

    public ExecutionTimeline() {
    }

    public String getEventId() { return eventId; }
    public void setEventId(String eventId) { this.eventId = eventId; }

    public String getMissionId() { return missionId; }
    public void setMissionId(String missionId) { this.missionId = missionId; }

    public String getEventType() { return eventType; }
    public void setEventType(String eventType) { this.eventType = eventType; }

    public String getEventDetails() { return eventDetails; }
    public void setEventDetails(String eventDetails) { this.eventDetails = eventDetails; }

    public Instant getTimestamp() { return timestamp; }
    public void setTimestamp(Instant timestamp) { this.timestamp = timestamp; }
}
