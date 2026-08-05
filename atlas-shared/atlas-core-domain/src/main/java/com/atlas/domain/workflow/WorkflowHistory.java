package com.atlas.domain.workflow;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;

/**
 * Audit trail log record for individual workflow state changes and step events.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class WorkflowHistory {
    private String id;
    private String instanceId;
    private String stepId;
    private String eventType;
    private String details;
    private Instant timestamp;
}
