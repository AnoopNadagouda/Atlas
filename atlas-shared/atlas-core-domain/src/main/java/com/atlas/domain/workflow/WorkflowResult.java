package com.atlas.domain.workflow;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;
import java.util.Map;

/**
 * Result record produced upon workflow completion, failure, or cancellation.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class WorkflowResult {
    private String instanceId;
    private WorkflowState state;
    private Map<String, Object> outputs;
    private String errorMessage;
    private long totalDurationMs;
    private Instant completedAt;
}
