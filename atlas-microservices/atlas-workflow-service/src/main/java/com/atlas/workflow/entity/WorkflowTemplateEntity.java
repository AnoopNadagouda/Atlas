package com.atlas.workflow.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "workflow_templates")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class WorkflowTemplateEntity {

    @Id
    private String id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String category;

    @Column(length = 2000)
    private String description;

    @Column(columnDefinition = "TEXT", nullable = false)
    private String definitionJson;

    private String tags;
}
