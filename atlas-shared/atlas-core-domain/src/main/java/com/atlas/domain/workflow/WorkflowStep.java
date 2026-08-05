package com.atlas.domain.workflow;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.Map;

/**
 * Domain model representing a single step node in a Workflow DAG topology.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class WorkflowStep {
    private String stepId;
    private String name;
    private String description;
    private StepType type;
    private List<String> dependsOn;
    private Map<String, Object> parameters;
    @Builder.Default
    private int maxRetries = 3;
    @Builder.Default
    private long timeoutMs = 30000L;
    private String conditionExpression;
    private String agentId;
    private String connectorId;
    private String approvalRole;
}
