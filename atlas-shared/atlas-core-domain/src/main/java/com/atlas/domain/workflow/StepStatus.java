package com.atlas.domain.workflow;

/**
 * Execution status for individual workflow steps.
 */
public enum StepStatus {
    PENDING,
    RUNNING,
    WAITING_FOR_APPROVAL,
    COMPLETED,
    FAILED,
    SKIPPED;

    public boolean isTerminal() {
        return this == COMPLETED || this == FAILED || this == SKIPPED;
    }
}
