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
@Table(name = "workflow_events")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class WorkflowEventEntity {

    @Id
    private String id;

    @Column(nullable = false)
    private String instanceId;

    private String stepId;

    @Column(nullable = false)
    private String eventType;

    @Column(columnDefinition = "TEXT")
    private String details;

    @Column(nullable = false)
    private Instant timestamp;
}
