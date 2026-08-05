package com.atlas.domain.agent.tool;

/**
 * Outcome status codes for tool execution attempts.
 */
public enum ToolExecutionStatus {
    SUCCESS,
    VALIDATION_ERROR,
    PERMISSION_DENIED,
    TIMEOUT,
    EXECUTION_ERROR,
    CANCELLED;

    public boolean isSuccess() {
        return this == SUCCESS;
    }
}
