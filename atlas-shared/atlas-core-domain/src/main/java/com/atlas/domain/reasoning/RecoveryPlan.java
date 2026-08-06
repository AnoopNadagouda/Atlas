package com.atlas.domain.reasoning;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

public class RecoveryPlan {
    private String id;
    private String sessionId;
    private String failureReason;
    private String rootCause;
    private String recoveryStrategy; // FALLBACK_TOOL, ALTERNATE_PATH, RETRY_WITH_CONTEXT, REPLAN_SUBTASK
    private List<String> recoverySteps = new ArrayList<>();
    private boolean executedSuccessfully;
    private Instant timestamp;

    public RecoveryPlan() {
    }

    public RecoveryPlan(String id, String sessionId, String failureReason, String rootCause, String recoveryStrategy, List<String> recoverySteps, boolean executedSuccessfully) {
        this.id = id;
        this.sessionId = sessionId;
        this.failureReason = failureReason;
        this.rootCause = rootCause;
        this.recoveryStrategy = recoveryStrategy;
        this.recoverySteps = recoverySteps;
        this.executedSuccessfully = executedSuccessfully;
        this.timestamp = Instant.now();
    }

    public String getId() { return id; }
    public void setId(String id) { this.id = id; }

    public String getSessionId() { return sessionId; }
    public void setSessionId(String sessionId) { this.sessionId = sessionId; }

    public String getFailureReason() { return failureReason; }
    public void setFailureReason(String failureReason) { this.failureReason = failureReason; }

    public String getRootCause() { return rootCause; }
    public void setRootCause(String rootCause) { this.rootCause = rootCause; }

    public String getRecoveryStrategy() { return recoveryStrategy; }
    public void setRecoveryStrategy(String recoveryStrategy) { this.recoveryStrategy = recoveryStrategy; }

    public List<String> getRecoverySteps() { return recoverySteps; }
    public void setRecoverySteps(List<String> recoverySteps) { this.recoverySteps = recoverySteps; }

    public boolean isExecutedSuccessfully() { return executedSuccessfully; }
    public void setExecutedSuccessfully(boolean executedSuccessfully) { this.executedSuccessfully = executedSuccessfully; }

    public Instant getTimestamp() { return timestamp; }
    public void setTimestamp(Instant timestamp) { this.timestamp = timestamp; }
}
