package com.atlas.domain.workflow;

import lombok.Getter;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Thread-safe execution context for passing variables and data across steps in a Workflow DAG.
 */
public class WorkflowContext {
    @Getter
    private final String instanceId;
    @Getter
    private final String definitionId;
    private final Map<String, Object> variables = new ConcurrentHashMap<>();
    private final Map<String, StepStatus> stepStatuses = new ConcurrentHashMap<>();
    private final Map<String, Object> stepResults = new ConcurrentHashMap<>();

    public WorkflowContext(String instanceId, String definitionId, Map<String, Object> initialVariables) {
        this.instanceId = instanceId;
        this.definitionId = definitionId;
        if (initialVariables != null) {
            this.variables.putAll(initialVariables);
        }
    }

    public Object getVariable(String key) {
        return variables.get(key);
    }

    public void setVariable(String key, Object value) {
        if (value != null) {
            variables.put(key, value);
        }
    }

    public Map<String, Object> getAllVariables() {
        return new ConcurrentHashMap<>(variables);
    }

    public void setStepStatus(String stepId, StepStatus status) {
        stepStatuses.put(stepId, status);
    }

    public StepStatus getStepStatus(String stepId) {
        return stepStatuses.getOrDefault(stepId, StepStatus.PENDING);
    }

    public void setStepResult(String stepId, Object result) {
        if (result != null) {
            stepResults.put(stepId, result);
        }
    }

    public Object getStepResult(String stepId) {
        return stepResults.get(stepId);
    }
}
