package com.atlas.domain.reasoning;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

public class CritiqueRecord {
    private String id;
    private String sessionId;
    private String stepId;
    private double qualityScore;
    private List<String> flawsDetected = new ArrayList<>();
    private List<String> suggestions = new ArrayList<>();
    private boolean requiresRevision;
    private Instant timestamp;

    public CritiqueRecord() {
    }

    public CritiqueRecord(String id, String sessionId, String stepId, double qualityScore, List<String> flawsDetected, List<String> suggestions, boolean requiresRevision) {
        this.id = id;
        this.sessionId = sessionId;
        this.stepId = stepId;
        this.qualityScore = qualityScore;
        this.flawsDetected = flawsDetected;
        this.suggestions = suggestions;
        this.requiresRevision = requiresRevision;
        this.timestamp = Instant.now();
    }

    public String getId() { return id; }
    public void setId(String id) { this.id = id; }

    public String getSessionId() { return sessionId; }
    public void setSessionId(String sessionId) { this.sessionId = sessionId; }

    public String getStepId() { return stepId; }
    public void setStepId(String stepId) { this.stepId = stepId; }

    public double getQualityScore() { return qualityScore; }
    public void setQualityScore(double qualityScore) { this.qualityScore = qualityScore; }

    public List<String> getFlawsDetected() { return flawsDetected; }
    public void setFlawsDetected(List<String> flawsDetected) { this.flawsDetected = flawsDetected; }

    public List<String> getSuggestions() { return suggestions; }
    public void setSuggestions(List<String> suggestions) { this.suggestions = suggestions; }

    public boolean isRequiresRevision() { return requiresRevision; }
    public void setRequiresRevision(boolean requiresRevision) { this.requiresRevision = requiresRevision; }

    public Instant getTimestamp() { return timestamp; }
    public void setTimestamp(Instant timestamp) { this.timestamp = timestamp; }
}
