package com.atlas.domain.agent;

/**
 * Event listener for monitoring agent state machine transitions and execution steps.
 */
public interface AgentLifecycleListener {
    default void onStateTransition(AgentContext context, AgentState previousState, AgentState newState) {}
    default void onStepCompleted(AgentContext context, int stepNumber, String stepDetails) {}
    default void onError(AgentContext context, Throwable throwable) {}
    default void onCompleted(AgentContext context, AgentResult result) {}
}
