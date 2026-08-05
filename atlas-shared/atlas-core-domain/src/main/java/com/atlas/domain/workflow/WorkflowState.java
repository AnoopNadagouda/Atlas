package com.atlas.domain.workflow;

/**
 * Lifecycle states for Workflow Definitions and Executable Instances.
 */
public enum WorkflowState {
    DRAFT,
    ACTIVE,
    PAUSED,
    RUNNING,
    WAITING_FOR_APPROVAL,
    COMPLETED,
    FAILED,
    CANCELLED,
    ROLLED_BACK;

    public boolean isTerminal() {
        return this == COMPLETED || this == FAILED || this == CANCELLED || this == ROLLED_BACK;
    }
}
