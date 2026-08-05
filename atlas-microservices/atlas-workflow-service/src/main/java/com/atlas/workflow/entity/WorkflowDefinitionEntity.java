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
@Table(name = "workflow_definitions")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class WorkflowDefinitionEntity {

    @Id
    private String id;

    @Column(nullable = false)
    private String name;

    @Column(length = 2000)
    private String description;

    @Column(nullable = false)
    private String version;

    @Column(nullable = false)
    private String state;

    @Column(columnDefinition = "TEXT")
    private String definitionJson;

    private String cronSchedule;

    @Column(nullable = false, updatable = false)
    private Instant createdAt;

    @Column(nullable = false)
    private Instant updatedAt;
}
