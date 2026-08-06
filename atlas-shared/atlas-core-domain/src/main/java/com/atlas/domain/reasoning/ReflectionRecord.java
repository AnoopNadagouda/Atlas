package com.atlas.domain.reasoning;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

public class ReflectionRecord {
    private String id;
    private String sessionId;
    private String taskId;
    private double correctnessScore;
    private double efficiencyScore;
    private boolean hallucinationDetected;
    private String expectedOutcome;
    private String actualOutcome;
    private String critiqueSummary;
    private List<String> lessonsLearned = new ArrayList<>();
    private Instant timestamp;

    public ReflectionRecord() {
    }

    public ReflectionRecord(String id, String sessionId, String taskId, double correctnessScore, double efficiencyScore, boolean hallucinationDetected, String expectedOutcome, String actualOutcome, String critiqueSummary, List<String> lessonsLearned) {
        this.id = id;
        this.sessionId = sessionId;
        this.taskId = taskId;
        this.correctnessScore = correctnessScore;
        this.efficiencyScore = efficiencyScore;
        this.hallucinationDetected = hallucinationDetected;
        this.expectedOutcome = expectedOutcome;
        this.actualOutcome = actualOutcome;
        this.critiqueSummary = critiqueSummary;
        this.lessonsLearned = lessonsLearned;
        this.timestamp = Instant.now();
    }

    public String getId() { return id; }
    public void setId(String id) { this.id = id; }

    public String getSessionId() { return sessionId; }
    public void setSessionId(String sessionId) { this.sessionId = sessionId; }

    public String getTaskId() { return taskId; }
    public void setTaskId(String taskId) { this.taskId = taskId; }

    public double getCorrectnessScore() { return correctnessScore; }
    public void setCorrectnessScore(double correctnessScore) { this.correctnessScore = correctnessScore; }

    public double getEfficiencyScore() { return efficiencyScore; }
    public void setEfficiencyScore(double efficiencyScore) { this.efficiencyScore = efficiencyScore; }

    public boolean isHallucinationDetected() { return hallucinationDetected; }
    public void setHallucinationDetected(boolean hallucinationDetected) { this.hallucinationDetected = hallucinationDetected; }

    public String getExpectedOutcome() { return expectedOutcome; }
    public void setExpectedOutcome(String expectedOutcome) { this.expectedOutcome = expectedOutcome; }

    public String getActualOutcome() { return actualOutcome; }
    public void setActualOutcome(String actualOutcome) { this.actualOutcome = actualOutcome; }

    public String getCritiqueSummary() { return critiqueSummary; }
    public void setCritiqueSummary(String critiqueSummary) { this.critiqueSummary = critiqueSummary; }

    public List<String> getLessonsLearned() { return lessonsLearned; }
    public void setLessonsLearned(List<String> lessonsLearned) { this.lessonsLearned = lessonsLearned; }

    public Instant getTimestamp() { return timestamp; }
    public void setTimestamp(Instant timestamp) { this.timestamp = timestamp; }
}
