package com.atlas.domain.reasoning;

public class ThoughtEdge {
    private String id;
    private String sourceNodeId;
    private String targetNodeId;
    private String relationType; // LEADS_TO, REFINES, CONTRADICTS, PRUNES
    private double weight;

    public ThoughtEdge() {
    }

    public ThoughtEdge(String id, String sourceNodeId, String targetNodeId, String relationType, double weight) {
        this.id = id;
        this.sourceNodeId = sourceNodeId;
        this.targetNodeId = targetNodeId;
        this.relationType = relationType;
        this.weight = weight;
    }

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
