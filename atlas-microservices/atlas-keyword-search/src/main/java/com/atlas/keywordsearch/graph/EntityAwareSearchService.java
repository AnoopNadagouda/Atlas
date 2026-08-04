package com.atlas.keywordsearch.graph;

import com.atlas.domain.graph.EntityNode;
import com.atlas.domain.graph.RelationshipEdge;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.*;

@Slf4j
@Service
@RequiredArgsConstructor
public class EntityAwareSearchService {

    private final KnowledgeGraphService knowledgeGraphService;

    public Map<String, Object> enrichQueryWithGraphContext(String query) {
        if (query == null || query.isBlank()) {
            return Collections.emptyMap();
        }

        EntityNode entity = knowledgeGraphService.getEntityByName(query);
        if (entity == null) {
            List<EntityNode> matches = knowledgeGraphService.searchGraph(query, 1);
            if (!matches.isEmpty()) {
                entity = matches.get(0);
            }
        }

        if (entity == null) {
            return Collections.emptyMap();
        }

        log.info("Entity-Aware Query match found: '{}' ({})", entity.getName(), entity.getType());
        List<RelationshipEdge> neighbors = knowledgeGraphService.getEntityNeighbors(entity.getId());

        List<Map<String, Object>> connectedFacts = new ArrayList<>();
        for (RelationshipEdge edge : neighbors) {
            String targetId = edge.getSourceEntityId().equals(entity.getId()) ? edge.getTargetEntityId() : edge.getSourceEntityId();
            EntityNode neighborNode = knowledgeGraphService.getEntityByName(targetId);
            String neighborName = neighborNode != null ? neighborNode.getName() : targetId;

            connectedFacts.add(Map.of(
                    "relation", edge.getRelationType().toString(),
                    "connectedEntity", neighborName,
                    "confidence", edge.getConfidenceScore()
            ));
        }

        return Map.of(
                "entityId", entity.getId(),
                "entityName", entity.getName(),
                "canonicalName", entity.getCanonicalName(),
                "type", entity.getType().toString(),
                "aliases", entity.getAliases() != null ? entity.getAliases() : Collections.emptySet(),
                "connectedFacts", connectedFacts
        );
    }
}
