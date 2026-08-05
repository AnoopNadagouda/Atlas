package com.atlas.domain.workflow;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;
import java.util.Map;

/**
 * Snapshot checkpoint container enabling workflow rollback, pause/resume, and crash recovery.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class WorkflowCheckpoint {
    private String checkpointId;
    private String instanceId;
    private String stepId;
    private WorkflowState state;
    private Map<String, Object> contextVariables;
    private Instant createdAt;
}
