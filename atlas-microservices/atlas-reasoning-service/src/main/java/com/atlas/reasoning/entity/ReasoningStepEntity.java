package com.atlas.reasoning.entity;

import com.atlas.domain.reasoning.ReasoningMode;
import jakarta.persistence.*;

import java.time.Instant;

@Entity
@Table(name = "reasoning_steps")
public class ReasoningStepEntity {

    @Id
    private String id;

    @Column(name = "session_id", nullable = false)
    private String sessionId;

    @Column(name = "step_number")
    private int stepNumber;

    @Enumerated(EnumType.STRING)
    @Column(name = "step_mode")
    private ReasoningMode stepMode;

    @Column(name = "thought", length = 2048)
    private String thought;

    @Column(name = "action", length = 1024)
    private String action;

    @Column(name = "observation", length = 2048)
    private String observation;

    @Column(name = "confidence")
    private double confidence;

    @Column(name = "timestamp", nullable = false)
    private Instant timestamp;

    public ReasoningStepEntity() {
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
