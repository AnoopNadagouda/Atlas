package com.atlas.common.dto.workflow;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;
import java.util.Map;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class WorkflowStatusResponse {
    private String instanceId;
    private String definitionId;
    private String state;
    private String currentStepId;
    private Map<String, Object> outputValues;
    private String errorMessage;
    private Instant startTime;
    private Instant endTime;
    private long executionTimeMs;
}
