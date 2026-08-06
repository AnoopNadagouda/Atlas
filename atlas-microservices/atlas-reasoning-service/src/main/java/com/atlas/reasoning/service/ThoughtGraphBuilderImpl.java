package com.atlas.reasoning.service;

import com.atlas.domain.reasoning.ThoughtEdge;
import com.atlas.domain.reasoning.ThoughtNode;
import com.atlas.reasoning.entity.ThoughtEdgeEntity;
import com.atlas.reasoning.entity.ThoughtNodeEntity;
import com.atlas.reasoning.repository.ThoughtEdgeRepository;
import com.atlas.reasoning.repository.ThoughtNodeRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.List;
import java.util.UUID;

@Service
public class ThoughtGraphBuilderImpl implements ThoughtGraphBuilder {

    private static final Logger log = LoggerFactory.getLogger(ThoughtGraphBuilderImpl.class);

    private final ThoughtNodeRepository nodeRepository;
    private final ThoughtEdgeRepository edgeRepository;

    public ThoughtGraphBuilderImpl(ThoughtNodeRepository nodeRepository, ThoughtEdgeRepository edgeRepository) {
        this.nodeRepository = nodeRepository;
        this.edgeRepository = edgeRepository;
    }

    @Override
    public ThoughtNode addThoughtNode(String sessionId, String content, String type, double score) {
        log.info("[ThoughtGraphBuilder] Adding thought node to session '{}'", sessionId);
        ThoughtNodeEntity entity = new ThoughtNodeEntity();
        entity.setId(UUID.randomUUID().toString());
        entity.setSessionId(sessionId);
        entity.setThoughtContent(content);
        entity.setNodeType(type != null ? type : "THOUGHT_ROOT");
        entity.setScore(score);
        entity.setDepth(1.0);
        entity.setCreatedAt(Instant.now());

        nodeRepository.save(entity);
        return mapNodeToDomain(entity);
    }

    @Override
    public ThoughtEdge linkThoughtNodes(String sourceId, String targetId, String relation, double weight) {
        log.info("[ThoughtGraphBuilder] Linking thought node {} -> {} ({})", sourceId, targetId, relation);
        ThoughtEdgeEntity entity = new ThoughtEdgeEntity();
        entity.setId(UUID.randomUUID().toString());
        entity.setSourceNodeId(sourceId);
        entity.setTargetNodeId(targetId);
        entity.setRelationType(relation != null ? relation : "LEADS_TO");
        entity.setWeight(weight > 0 ? weight : 1.0);

        edgeRepository.save(entity);
        return mapEdgeToDomain(entity);
    }

    @Override
    public List<ThoughtNode> getThoughtGraphNodes(String sessionId) {
        return nodeRepository.findBySessionId(sessionId).stream().map(this::mapNodeToDomain).toList();
    }

    private ThoughtNode mapNodeToDomain(ThoughtNodeEntity entity) {
        ThoughtNode node = new ThoughtNode();
        node.setId(entity.getId());
        node.setSessionId(entity.getSessionId());
        node.setContent(entity.getThoughtContent());
        node.setType(entity.getNodeType());
        node.setScore(entity.getScore());
        node.setSelected(entity.getScore() > 0.8);
        return node;
    }

    private ThoughtEdge mapEdgeToDomain(ThoughtEdgeEntity entity) {
        ThoughtEdge edge = new ThoughtEdge();
        edge.setId(entity.getId());
        edge.setSourceNodeId(entity.getSourceNodeId());
        edge.setTargetNodeId(entity.getTargetNodeId());
        edge.setRelationType(entity.getRelationType());
        edge.setWeight(entity.getWeight());
        return edge;
    }
}
