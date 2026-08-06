package com.atlas.reasoning.service;

import com.atlas.domain.reasoning.ThoughtNode;
import com.atlas.domain.reasoning.ThoughtEdge;
import java.util.List;

public interface ThoughtGraphBuilder {
    ThoughtNode addThoughtNode(String sessionId, String content, String type, double score);
    ThoughtEdge linkThoughtNodes(String sourceId, String targetId, String relation, double weight);
    List<ThoughtNode> getThoughtGraphNodes(String sessionId);
}
