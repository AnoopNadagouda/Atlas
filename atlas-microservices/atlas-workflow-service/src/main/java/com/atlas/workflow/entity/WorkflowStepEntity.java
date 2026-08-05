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
@Table(name = "workflow_steps")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class WorkflowStepEntity {

    @Id
    private String id;

    @Column(nullable = false)
    private String instanceId;

    @Column(nullable = false)
    private String stepId;

    @Column(nullable = false)
    private String stepName;

    @Column(nullable = false)
    private String stepType;

    @Column(nullable = false)
    private String status;

    @Column(columnDefinition = "TEXT")
    private String resultJson;

    @Column(length = 2000)
    private String errorMessage;

    private int retryCount;

    private Instant startTime;

    private Instant endTime;

    private long durationMs;
}
