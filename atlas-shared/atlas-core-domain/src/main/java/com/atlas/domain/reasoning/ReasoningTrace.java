package com.atlas.domain.reasoning;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

public class ReasoningTrace {
    private String id;
    private String sessionId;
    private String goalDescription;
    private List<ThoughtNode> nodes = new ArrayList<>();
    private List<ThoughtEdge> edges = new ArrayList<>();
    private Instant createdAt;

    public ReasoningTrace() {
    }

    public ReasoningTrace(String id, String sessionId, String goalDescription, List<ThoughtNode> nodes, List<ThoughtEdge> edges) {
        this.id = id;
        this.sessionId = sessionId;
        this.goalDescription = goalDescription;
        this.nodes = nodes;
        this.edges = edges;
        this.createdAt = Instant.now();
    }

    public String getId() { return id; }
    public void setId(String id) { this.id = id; }

    public String getSessionId() { return sessionId; }
    public void setSessionId(String sessionId) { this.sessionId = sessionId; }

    public String getGoalDescription() { return goalDescription; }
    public void setGoalDescription(String goalDescription) { this.goalDescription = goalDescription; }

    public List<ThoughtNode> getNodes() { return nodes; }
    public void setNodes(List<ThoughtNode> nodes) { this.nodes = nodes; }

    public List<ThoughtEdge> getEdges() { return edges; }
    public void setEdges(List<ThoughtEdge> edges) { this.edges = edges; }

    public Instant getCreatedAt() { return createdAt; }
    public void setCreatedAt(Instant createdAt) { this.createdAt = createdAt; }
}
