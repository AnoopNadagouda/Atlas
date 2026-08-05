package com.atlas.workflow.engine;

import com.atlas.domain.workflow.WorkflowDefinition;
import com.atlas.domain.workflow.WorkflowInstance;
import com.atlas.domain.workflow.WorkflowResult;

import java.util.List;
import java.util.Map;

public interface WorkflowEngine {
    WorkflowDefinition createWorkflow(WorkflowDefinition definition);
    WorkflowDefinition validateWorkflow(String rawContent, boolean isYaml);
    WorkflowInstance executeWorkflow(String definitionId, Map<String, Object> inputValues);
    WorkflowResult pauseWorkflow(String instanceId);
    WorkflowResult resumeWorkflow(String instanceId);
    WorkflowResult cancelWorkflow(String instanceId);
    WorkflowResult restartWorkflow(String instanceId);
    boolean processApproval(String instanceId, String stepId, boolean approved, String approverId, String comments);
    WorkflowInstance getWorkflowStatus(String instanceId);
    List<WorkflowInstance> getWorkflowHistory(String definitionId);
    Map<String, Object> getWorkflowStatistics();
}
