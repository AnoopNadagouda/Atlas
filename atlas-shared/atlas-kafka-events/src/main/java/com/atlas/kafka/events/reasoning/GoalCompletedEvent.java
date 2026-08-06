package com.atlas.kafka.events.reasoning;

import java.time.Instant;

public class GoalCompletedEvent {
    private String eventId;
    private String goalId;
    private String tenantId;
    private String title;
    private Instant timestamp;

    public GoalCompletedEvent() {
    }

    public GoalCompletedEvent(String eventId, String goalId, String tenantId, String title) {
        this.eventId = eventId;
        this.goalId = goalId;
        this.tenantId = tenantId;
        this.title = title;
        this.timestamp = Instant.now();
    }

    public String getEventId() { return eventId; }
    public void setEventId(String eventId) { this.eventId = eventId; }

    public String getGoalId() { return goalId; }
    public void setGoalId(String goalId) { this.goalId = goalId; }

    public String getTenantId() { return tenantId; }
    public void setTenantId(String tenantId) { this.tenantId = tenantId; }

    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }

    public Instant getTimestamp() { return timestamp; }
    public void setTimestamp(Instant timestamp) { this.timestamp = timestamp; }
}
