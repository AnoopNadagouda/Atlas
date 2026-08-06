package com.atlas.reasoning.entity;

import jakarta.persistence.*;

import java.time.Instant;

@Entity
@Table(name = "critique_records")
public class CritiqueRecordEntity {

    @Id
    private String id;

    @Column(name = "session_id", nullable = false)
    private String sessionId;

    @Column(name = "step_id")
    private String stepId;

    @Column(name = "quality_score")
    private double qualityScore;

    @Column(name = "flaws_detected", length = 2048)
    private String flawsDetectedJson;

    @Column(name = "suggestions", length = 2048)
    private String suggestionsJson;

    @Column(name = "requires_revision")
    private boolean requiresRevision;

    @Column(name = "timestamp", nullable = false)
    private Instant timestamp;

    public CritiqueRecordEntity() {
    }

    public String getId() { return id; }
    public void setId(String id) { this.id = id; }

    public String getSessionId() { return sessionId; }
    public void setSessionId(String sessionId) { this.sessionId = sessionId; }

    public String getStepId() { return stepId; }
    public void setStepId(String stepId) { this.stepId = stepId; }

    public double getQualityScore() { return qualityScore; }
    public void setQualityScore(double qualityScore) { this.qualityScore = qualityScore; }

    public String getFlawsDetectedJson() { return flawsDetectedJson; }
    public void setFlawsDetectedJson(String flawsDetectedJson) { this.flawsDetectedJson = flawsDetectedJson; }

    public String getSuggestionsJson() { return suggestionsJson; }
    public void setSuggestionsJson(String suggestionsJson) { this.suggestionsJson = suggestionsJson; }

    public boolean isRequiresRevision() { return requiresRevision; }
    public void setRequiresRevision(boolean requiresRevision) { this.requiresRevision = requiresRevision; }

    public Instant getTimestamp() { return timestamp; }
    public void setTimestamp(Instant timestamp) { this.timestamp = timestamp; }
}
