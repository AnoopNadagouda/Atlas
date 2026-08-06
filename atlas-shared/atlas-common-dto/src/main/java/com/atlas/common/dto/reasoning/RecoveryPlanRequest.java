package com.atlas.common.dto.reasoning;

public class RecoveryPlanRequest {
    private String sessionId;
    private String failureReason;
    private String failedStepId;

    public RecoveryPlanRequest() {
    }

    public String getSessionId() { return sessionId; }
    public void setSessionId(String sessionId) { this.sessionId = sessionId; }

    public String getFailureReason() { return failureReason; }
    public void setFailureReason(String failureReason) { this.failureReason = failureReason; }

    public String getFailedStepId() { return failedStepId; }
    public void setFailedStepId(String failedStepId) { this.failedStepId = failedStepId; }
}
