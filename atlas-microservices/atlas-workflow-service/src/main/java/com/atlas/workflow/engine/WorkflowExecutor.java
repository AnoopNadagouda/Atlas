package com.atlas.workflow.engine;

import com.atlas.domain.workflow.WorkflowDefinition;
import com.atlas.domain.workflow.WorkflowInstance;
import com.atlas.domain.workflow.WorkflowResult;

import java.util.Map;

public interface WorkflowExecutor {
    WorkflowInstance executeWorkflow(WorkflowDefinition definition, Map<String, Object> inputValues);
    WorkflowResult pauseWorkflow(String instanceId);
    WorkflowResult resumeWorkflow(String instanceId);
    WorkflowResult cancelWorkflow(String instanceId);
    WorkflowResult restartWorkflow(String instanceId);
}
