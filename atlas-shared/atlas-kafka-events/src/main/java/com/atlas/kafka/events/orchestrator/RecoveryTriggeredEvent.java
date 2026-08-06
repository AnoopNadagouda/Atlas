package com.atlas.kafka.events.orchestrator;

import java.time.Instant;

public class RecoveryTriggeredEvent {
    private String eventId;
    private String missionId;
    private String failureCause;
    private String recoveryStrategy;
    private Instant timestamp;

    public RecoveryTriggeredEvent() {
    }

    public RecoveryTriggeredEvent(String eventId, String missionId, String failureCause, String recoveryStrategy) {
        this.eventId = eventId;
        this.missionId = missionId;
        this.failureCause = failureCause;
        this.recoveryStrategy = recoveryStrategy;
        this.timestamp = Instant.now();
    }

    public String getEventId() { return eventId; }
    public void setEventId(String eventId) { this.eventId = eventId; }

    public String getMissionId() { return missionId; }
    public void setMissionId(String missionId) { this.missionId = missionId; }

    public String getFailureCause() { return failureCause; }
    public void setFailureCause(String failureCause) { this.failureCause = failureCause; }

    public String getRecoveryStrategy() { return recoveryStrategy; }
    public void setRecoveryStrategy(String recoveryStrategy) { this.recoveryStrategy = recoveryStrategy; }

    public Instant getTimestamp() { return timestamp; }
    public void setTimestamp(Instant timestamp) { this.timestamp = timestamp; }
}
