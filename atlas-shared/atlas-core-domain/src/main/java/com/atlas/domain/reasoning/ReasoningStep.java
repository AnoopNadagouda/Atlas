package com.atlas.domain.reasoning;

import java.time.Instant;

public class ReasoningStep {
    private String id;
    private String sessionId;
    private int stepNumber;
    private ReasoningMode stepMode;
    private String thought;
    private String action;
    private String observation;
    private double confidence;
    private Instant timestamp;

    public ReasoningStep() {
    }

    public ReasoningStep(String id, String sessionId, int stepNumber, ReasoningMode stepMode, String thought, String action, String observation, double confidence) {
        this.id = id;
        this.sessionId = sessionId;
        this.stepNumber = stepNumber;
        this.stepMode = stepMode;
        this.thought = thought;
        this.action = action;
        this.observation = observation;
        this.confidence = confidence;
        this.timestamp = Instant.now();
    }

    public String getId() { return id; }
    public void setId(String id) { this.id = id; }

    public String getSessionId() { return sessionId; }
    public void setSessionId(String sessionId) { this.sessionId = sessionId; }

    public int getStepNumber() { return stepNumber; }
    public void setStepNumber(int stepNumber) { this.stepNumber = stepNumber; }

    public ReasoningMode getStepMode() { return stepMode; }
    public void setStepMode(ReasoningMode stepMode) { this.stepMode = stepMode; }

    public String getThought() { return thought; }
    public void setThought(String thought) { this.thought = thought; }

    public String getAction() { return action; }
    public void setAction(String action) { this.action = action; }

    public String getObservation() { return observation; }
    public void setObservation(String observation) { this.observation = observation; }

    public double getConfidence() { return confidence; }
    public void setConfidence(double confidence) { this.confidence = confidence; }

    public Instant getTimestamp() { return timestamp; }
    public void setTimestamp(Instant timestamp) { this.timestamp = timestamp; }
}
