package com.atlas.common.dto.workflow;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.Map;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class WorkflowCreateRequest {
    private String name;
    private String description;
    private String version;
    private List<WorkflowStepDto> steps;
    private Map<String, Object> inputParameters;
    private String cronSchedule;

    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class WorkflowStepDto {
        private String stepId;
        private String name;
        private String description;
        private String type; // SEQUENTIAL, PARALLEL, CONDITION, LOOP, HUMAN_APPROVAL, AGENT_EXECUTION, CONNECTOR_EXECUTION, EVENT_WAIT
        private List<String> dependsOn;
        private Map<String, Object> parameters;
        private Integer maxRetries;
        private Long timeoutMs;
        private String conditionExpression;
        private String agentId;
        private String connectorId;
        private String approvalRole;
    }
}
