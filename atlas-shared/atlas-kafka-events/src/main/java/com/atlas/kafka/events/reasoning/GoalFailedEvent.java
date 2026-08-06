package com.atlas.kafka.events.reasoning;

import java.time.Instant;

public class GoalFailedEvent {
    private String eventId;
    private String goalId;
    private String tenantId;
    private String failureReason;
    private Instant timestamp;

    public GoalFailedEvent() {
    }

    public GoalFailedEvent(String eventId, String goalId, String tenantId, String failureReason) {
        this.eventId = eventId;
        this.goalId = goalId;
        this.tenantId = tenantId;
        this.failureReason = failureReason;
        this.timestamp = Instant.now();
    }

    public String getEventId() { return eventId; }
    public void setEventId(String eventId) { this.eventId = eventId; }

    public String getGoalId() { return goalId; }
    public void setGoalId(String goalId) { this.goalId = goalId; }

    public String getTenantId() { return tenantId; }
    public void setTenantId(String tenantId) { this.tenantId = tenantId; }

    public String getFailureReason() { return failureReason; }
    public void setFailureReason(String failureReason) { this.failureReason = failureReason; }

    public Instant getTimestamp() { return timestamp; }
    public void setTimestamp(Instant timestamp) { this.timestamp = timestamp; }
}
