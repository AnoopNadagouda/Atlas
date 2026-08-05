package com.atlas.domain.agent;

/**
 * Lifecycle states for an Autonomous AI Agent execution session.
 */
public enum AgentState {
    CREATED,
    INITIALIZING,
    READY,
    RUNNING,
    PAUSED,
    WAITING_FOR_TOOL,
    SELF_CORRECTING,
    COMPLETED,
    FAILED;

    public boolean isTerminal() {
        return this == COMPLETED || this == FAILED;
    }

    public boolean canTransitionTo(AgentState targetState) {
        if (this == targetState) {
            return true;
        }
        if (isTerminal()) {
            return false; // Terminal states are final
        }
        return switch (this) {
            case CREATED -> targetState == INITIALIZING || targetState == FAILED;
            case INITIALIZING -> targetState == READY || targetState == FAILED;
            case READY -> targetState == RUNNING || targetState == PAUSED || targetState == FAILED;
            case RUNNING -> targetState == PAUSED || targetState == WAITING_FOR_TOOL || targetState == SELF_CORRECTING || targetState == COMPLETED || targetState == FAILED;
            case PAUSED -> targetState == RUNNING || targetState == FAILED;
            case WAITING_FOR_TOOL -> targetState == RUNNING || targetState == SELF_CORRECTING || targetState == FAILED;
            case SELF_CORRECTING -> targetState == RUNNING || targetState == FAILED;
            default -> false;
        };
    }
}
