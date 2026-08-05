package com.atlas.domain.workflow;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;
import java.util.List;
import java.util.Map;

/**
 * Domain model representing a Workflow Definition template and topology graph.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class WorkflowDefinition {
    private String id;
    private String name;
    private String description;
    @Builder.Default
    private String version = "1.0.0";
    private WorkflowState state;
    private List<WorkflowStep> steps;
    private Map<String, Object> inputParameters;
    private String cronSchedule;
    @Builder.Default
    private Instant createdAt = Instant.now();
    @Builder.Default
    private Instant updatedAt = Instant.now();
}
