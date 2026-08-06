package com.atlas.kafka.events.reasoning;

import java.time.Instant;

public class ReasoningStartedEvent {
    private String eventId;
    private String sessionId;
    private String tenantId;
    private String goalId;
    private String mode;
    private Instant timestamp;

    public ReasoningStartedEvent() {
    }

    public ReasoningStartedEvent(String eventId, String sessionId, String tenantId, String goalId, String mode) {
        this.eventId = eventId;
        this.sessionId = sessionId;
        this.tenantId = tenantId;
        this.goalId = goalId;
        this.mode = mode;
        this.timestamp = Instant.now();
    }

    public String getEventId() { return eventId; }
    public void setEventId(String eventId) { this.eventId = eventId; }

    public String getSessionId() { return sessionId; }
    public void setSessionId(String sessionId) { this.sessionId = sessionId; }

    public String getTenantId() { return tenantId; }
    public void setTenantId(String tenantId) { this.tenantId = tenantId; }

    public String getGoalId() { return goalId; }
    public void setGoalId(String goalId) { this.goalId = goalId; }

    public String getMode() { return mode; }
    public void setMode(String mode) { this.mode = mode; }

    public Instant getTimestamp() { return timestamp; }
    public void setTimestamp(Instant timestamp) { this.timestamp = timestamp; }
}
