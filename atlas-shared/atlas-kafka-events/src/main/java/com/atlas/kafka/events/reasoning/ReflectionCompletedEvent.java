package com.atlas.kafka.events.reasoning;

import java.time.Instant;

public class ReflectionCompletedEvent {
    private String eventId;
    private String reflectionId;
    private String sessionId;
    private double correctnessScore;
    private boolean hallucinationDetected;
    private Instant timestamp;

    public ReflectionCompletedEvent() {
    }

    public ReflectionCompletedEvent(String eventId, String reflectionId, String sessionId, double correctnessScore, boolean hallucinationDetected) {
        this.eventId = eventId;
        this.reflectionId = reflectionId;
        this.sessionId = sessionId;
        this.correctnessScore = correctnessScore;
        this.hallucinationDetected = hallucinationDetected;
        this.timestamp = Instant.now();
    }

    public String getEventId() { return eventId; }
    public void setEventId(String eventId) { this.eventId = eventId; }

    public String getReflectionId() { return reflectionId; }
    public void setReflectionId(String reflectionId) { this.reflectionId = reflectionId; }

    public String getSessionId() { return sessionId; }
    public void setSessionId(String sessionId) { this.sessionId = sessionId; }

    public double getCorrectnessScore() { return correctnessScore; }
    public void setCorrectnessScore(double correctnessScore) { this.correctnessScore = correctnessScore; }

    public boolean isHallucinationDetected() { return hallucinationDetected; }
    public void setHallucinationDetected(boolean hallucinationDetected) { this.hallucinationDetected = hallucinationDetected; }

    public Instant getTimestamp() { return timestamp; }
    public void setTimestamp(Instant timestamp) { this.timestamp = timestamp; }
}
