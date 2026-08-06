package com.atlas.reasoning.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "atlas_thought_edges", indexes = {
    @Index(name = "idx_tedge_source", columnList = "sourceNodeId"),
    @Index(name = "idx_tedge_target", columnList = "targetNodeId")
})
public class ThoughtEdgeEntity {

    @Id
    private String id;

    @Column(nullable = false)
    private String sourceNodeId;

    @Column(nullable = false)
    private String targetNodeId;

    private String relationType;
    private double weight;

    public ThoughtEdgeEntity() {}

    public String getId() { return id; }
    public void setId(String id) { this.id = id; }

    public String getSourceNodeId() { return sourceNodeId; }
    public void setSourceNodeId(String sourceNodeId) { this.sourceNodeId = sourceNodeId; }

    public String getTargetNodeId() { return targetNodeId; }
    public void setTargetNodeId(String targetNodeId) { this.targetNodeId = targetNodeId; }

    public String getRelationType() { return relationType; }
    public void setRelationType(String relationType) { this.relationType = relationType; }

    public double getWeight() { return weight; }
    public void setWeight(double weight) { this.weight = weight; }
}
