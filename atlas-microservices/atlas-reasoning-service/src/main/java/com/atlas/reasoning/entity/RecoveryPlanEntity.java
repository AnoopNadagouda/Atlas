package com.atlas.reasoning.entity;

import jakarta.persistence.*;

import java.time.Instant;

@Entity
@Table(name = "recovery_plans")
public class RecoveryPlanEntity {

    @Id
    private String id;

    @Column(name = "session_id", nullable = false)
    private String sessionId;

    @Column(name = "failure_reason", length = 1024)
    private String failureReason;

    @Column(name = "root_cause", length = 1024)
    private String rootCause;

    @Column(name = "recovery_strategy", nullable = false)
    private String recoveryStrategy;

    @Column(name = "recovery_steps", length = 2048)
    private String recoveryStepsJson;

    @Column(name = "executed_successfully")
    private boolean executedSuccessfully;

    @Column(name = "timestamp", nullable = false)
    private Instant timestamp;

    public RecoveryPlanEntity() {
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

    public String getRecoveryStepsJson() { return recoveryStepsJson; }
    public void setRecoveryStepsJson(String recoveryStepsJson) { this.recoveryStepsJson = recoveryStepsJson; }

    public boolean isExecutedSuccessfully() { return executedSuccessfully; }
    public void setExecutedSuccessfully(boolean executedSuccessfully) { this.executedSuccessfully = executedSuccessfully; }

    public Instant getTimestamp() { return timestamp; }
    public void setTimestamp(Instant timestamp) { this.timestamp = timestamp; }
}
