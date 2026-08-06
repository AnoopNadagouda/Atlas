package com.atlas.common.dto.reasoning;

public class CritiqueRequest {
    private String sessionId;
    private String stepId;
    private String outputContent;

    public CritiqueRequest() {
    }

    public String getSessionId() { return sessionId; }
    public void setSessionId(String sessionId) { this.sessionId = sessionId; }

    public String getStepId() { return stepId; }
    public void setStepId(String stepId) { this.stepId = stepId; }

    public String getOutputContent() { return outputContent; }
    public void setOutputContent(String outputContent) { this.outputContent = outputContent; }
}
