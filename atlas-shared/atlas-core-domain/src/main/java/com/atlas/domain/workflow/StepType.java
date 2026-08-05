package com.atlas.domain.workflow;

/**
 * Types of executable steps/nodes supported by the Workflow Engine.
 */
public enum StepType {
    SEQUENTIAL,
    PARALLEL,
    CONDITION,
    LOOP,
    HUMAN_APPROVAL,
    AGENT_EXECUTION,
    CONNECTOR_EXECUTION,
    EVENT_WAIT
}
