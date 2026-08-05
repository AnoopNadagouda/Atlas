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
@Table(name = "workflow_instances")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class WorkflowInstanceEntity {

    @Id
    private String instanceId;

    @Column(nullable = false)
    private String definitionId;

    @Column(nullable = false)
    private String state;

    @Column(columnDefinition = "TEXT")
    private String inputValuesJson;

    @Column(columnDefinition = "TEXT")
    private String outputValuesJson;

    private String currentStepId;

    @Column(length = 2000)
    private String errorMessage;

    @Column(nullable = false)
    private Instant startTime;

    private Instant endTime;

    private long executionTimeMs;
}
