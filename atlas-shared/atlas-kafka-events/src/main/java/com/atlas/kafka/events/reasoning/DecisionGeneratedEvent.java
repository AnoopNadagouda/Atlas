package com.atlas.kafka.events.reasoning;

import java.time.Instant;

public class DecisionGeneratedEvent {
    private String eventId;
    private String decisionId;
    private String sessionId;
    private String chosenOption;
    private double confidenceScore;
    private Instant timestamp;

    public DecisionGeneratedEvent() {
    }

    public DecisionGeneratedEvent(String eventId, String decisionId, String sessionId, String chosenOption, double confidenceScore) {
        this.eventId = eventId;
        this.decisionId = decisionId;
        this.sessionId = sessionId;
        this.chosenOption = chosenOption;
        this.confidenceScore = confidenceScore;
        this.timestamp = Instant.now();
    }

    public String getEventId() { return eventId; }
    public void setEventId(String eventId) { this.eventId = eventId; }

    public String getDecisionId() { return decisionId; }
    public void setDecisionId(String decisionId) { this.decisionId = decisionId; }

    public String getSessionId() { return sessionId; }
    public void setSessionId(String sessionId) { this.sessionId = sessionId; }

    public String getChosenOption() { return chosenOption; }
    public void setChosenOption(String chosenOption) { this.chosenOption = chosenOption; }

    public double getConfidenceScore() { return confidenceScore; }
    public void setConfidenceScore(double confidenceScore) { this.confidenceScore = confidenceScore; }

    public Instant getTimestamp() { return timestamp; }
    public void setTimestamp(Instant timestamp) { this.timestamp = timestamp; }
}
