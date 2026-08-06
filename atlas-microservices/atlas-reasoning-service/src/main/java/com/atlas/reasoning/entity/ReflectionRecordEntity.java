package com.atlas.reasoning.entity;

import jakarta.persistence.*;

import java.time.Instant;

@Entity
@Table(name = "reflection_records")
public class ReflectionRecordEntity {

    @Id
    private String id;

    @Column(name = "session_id", nullable = false)
    private String sessionId;

    @Column(name = "task_id")
    private String taskId;

    @Column(name = "correctness_score")
    private double correctnessScore;

    @Column(name = "efficiency_score")
    private double efficiencyScore;

    @Column(name = "hallucination_detected")
    private boolean hallucinationDetected;

    @Column(name = "expected_outcome", length = 2048)
    private String expectedOutcome;

    @Column(name = "actual_outcome", length = 2048)
    private String actualOutcome;

    @Column(name = "critique_summary", length = 2048)
    private String critiqueSummary;

    @Column(name = "lessons_learned", length = 2048)
    private String lessonsLearnedJson;

    @Column(name = "timestamp", nullable = false)
    private Instant timestamp;

    public ReflectionRecordEntity() {
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

    public String getLessonsLearnedJson() { return lessonsLearnedJson; }
    public void setLessonsLearnedJson(String lessonsLearnedJson) { this.lessonsLearnedJson = lessonsLearnedJson; }

    public Instant getTimestamp() { return timestamp; }
    public void setTimestamp(Instant timestamp) { this.timestamp = timestamp; }
}
