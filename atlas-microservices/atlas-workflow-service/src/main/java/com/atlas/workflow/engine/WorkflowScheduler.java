package com.atlas.workflow.engine;

public interface WorkflowScheduler {
    void scheduleWorkflow(String definitionId, String cronExpression);
    void unscheduleWorkflow(String definitionId);
}
