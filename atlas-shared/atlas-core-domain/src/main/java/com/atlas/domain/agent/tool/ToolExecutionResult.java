package com.atlas.domain.agent.tool;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;
import java.util.Map;

/**
 * Execution result containing status, payload output, timing, and error details.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ToolExecutionResult {
    private String toolId;
    private String executionId;
    private ToolExecutionStatus status;
    private Map<String, Object> output;
    private String errorMessage;
    private Instant startTime;
    private Instant endTime;
    private long latencyMs;

    public boolean isSuccess() {
        return status == ToolExecutionStatus.SUCCESS;
    }
}
