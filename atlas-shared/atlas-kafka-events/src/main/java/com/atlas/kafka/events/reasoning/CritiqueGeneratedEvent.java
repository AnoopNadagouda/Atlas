package com.atlas.kafka.events.reasoning;

import java.time.Instant;

public class CritiqueGeneratedEvent {
    private String eventId;
    private String critiqueId;
    private String sessionId;
    private double qualityScore;
    private boolean requiresRevision;
    private Instant timestamp;

    public CritiqueGeneratedEvent() {
    }

    public CritiqueGeneratedEvent(String eventId, String critiqueId, String sessionId, double qualityScore, boolean requiresRevision) {
        this.eventId = eventId;
        this.critiqueId = critiqueId;
        this.sessionId = sessionId;
        this.qualityScore = qualityScore;
        this.requiresRevision = requiresRevision;
        this.timestamp = Instant.now();
    }

    public String getEventId() { return eventId; }
    public void setEventId(String eventId) { this.eventId = eventId; }

    public String getCritiqueId() { return critiqueId; }
    public void setCritiqueId(String critiqueId) { this.critiqueId = critiqueId; }

    public String getSessionId() { return sessionId; }
    public void setSessionId(String sessionId) { this.sessionId = sessionId; }

    public double getQualityScore() { return qualityScore; }
    public void setQualityScore(double qualityScore) { this.qualityScore = qualityScore; }

    public boolean isRequiresRevision() { return requiresRevision; }
    public void setRequiresRevision(boolean requiresRevision) { this.requiresRevision = requiresRevision; }

    public Instant getTimestamp() { return timestamp; }
    public void setTimestamp(Instant timestamp) { this.timestamp = timestamp; }
}
