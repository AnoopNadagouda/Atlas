package com.atlas.kafka.events.reasoning;

import java.time.Instant;

public class ReasoningCompletedEvent {
    private String eventId;
    private String sessionId;
    private String tenantId;
    private String status;
    private double overallConfidence;
    private Instant timestamp;

    public ReasoningCompletedEvent() {
    }

    public ReasoningCompletedEvent(String eventId, String sessionId, String tenantId, String status, double overallConfidence) {
        this.eventId = eventId;
        this.sessionId = sessionId;
        this.tenantId = tenantId;
        this.status = status;
        this.overallConfidence = overallConfidence;
        this.timestamp = Instant.now();
    }

    public String getEventId() { return eventId; }
    public void setEventId(String eventId) { this.eventId = eventId; }

    public String getSessionId() { return sessionId; }
    public void setSessionId(String sessionId) { this.sessionId = sessionId; }

    public String getTenantId() { return tenantId; }
    public void setTenantId(String tenantId) { this.tenantId = tenantId; }

    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }

    public double getOverallConfidence() { return overallConfidence; }
    public void setOverallConfidence(double overallConfidence) { this.overallConfidence = overallConfidence; }

    public Instant getTimestamp() { return timestamp; }
    public void setTimestamp(Instant timestamp) { this.timestamp = timestamp; }
}
