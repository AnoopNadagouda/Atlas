package com.atlas.workflow.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;

@Entity
@Table(name = "workflow_checkpoints")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class WorkflowCheckpointEntity {

    @Id
    private String checkpointId;

    @Column(nullable = false)
    private String instanceId;

    private String stepId;

    @Column(nullable = false)
    private String state;

    @Column(columnDefinition = "TEXT")
    private String contextVariablesJson;

    @Column(nullable = false)
    private Instant createdAt;
}
