package com.atlas.domain.workflow;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;
import java.util.Map;

/**
 * Domain entity representing an active or finished Workflow Execution Instance.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class WorkflowInstance {
    private String instanceId;
    private String definitionId;
    private WorkflowState state;
    private Map<String, Object> inputValues;
    private Map<String, Object> outputValues;
    private String currentStepId;
    private String errorMessage;
    private Instant startTime;
    private Instant endTime;
    private long executionTimeMs;
}
