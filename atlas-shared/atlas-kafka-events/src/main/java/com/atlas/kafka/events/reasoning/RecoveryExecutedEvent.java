package com.atlas.kafka.events.reasoning;

import java.time.Instant;

public class RecoveryExecutedEvent {
    private String eventId;
    private String recoveryPlanId;
    private String sessionId;
    private String recoveryStrategy;
    private boolean executedSuccessfully;
    private Instant timestamp;

    public RecoveryExecutedEvent() {
    }

    public RecoveryExecutedEvent(String eventId, String recoveryPlanId, String sessionId, String recoveryStrategy, boolean executedSuccessfully) {
        this.eventId = eventId;
        this.recoveryPlanId = recoveryPlanId;
        this.sessionId = sessionId;
        this.recoveryStrategy = recoveryStrategy;
        this.executedSuccessfully = executedSuccessfully;
        this.timestamp = Instant.now();
    }

    public String getEventId() { return eventId; }
    public void setEventId(String eventId) { this.eventId = eventId; }

    public String getRecoveryPlanId() { return recoveryPlanId; }
    public void setRecoveryPlanId(String recoveryPlanId) { this.recoveryPlanId = recoveryPlanId; }

    public String getSessionId() { return sessionId; }
    public void setSessionId(String sessionId) { this.sessionId = sessionId; }

    public String getRecoveryStrategy() { return recoveryStrategy; }
    public void setRecoveryStrategy(String recoveryStrategy) { this.recoveryStrategy = recoveryStrategy; }

    public boolean isExecutedSuccessfully() { return executedSuccessfully; }
    public void setExecutedSuccessfully(boolean executedSuccessfully) { this.executedSuccessfully = executedSuccessfully; }

    public Instant getTimestamp() { return timestamp; }
    public void setTimestamp(Instant timestamp) { this.timestamp = timestamp; }
}
