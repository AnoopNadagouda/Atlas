package com.atlas.keywordsearch.graph;

import com.atlas.domain.graph.EntityNode;
import com.atlas.domain.graph.GraphStore;
import com.atlas.domain.graph.RelationshipEdge;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.*;

@Slf4j
@Service
@RequiredArgsConstructor
public class KnowledgeGraphService {

    private final GraphStore graphStore;
    private final RegexEntityExtractor entityExtractor;

    @PostConstruct
    public void initSeedGraph() {
        log.info("Initializing Knowledge Graph seed nodes and relationships...");
        List<EntityNode> seedEntities = entityExtractor.extractEntities("Atlas Spring Boot Apache Kafka Redis PostgreSQL Java");
        for (EntityNode entity : seedEntities) {
            graphStore.saveEntity(entity);
        }

        List<RelationshipEdge> relationships = entityExtractor.extractRelationships(seedEntities, "seed-doc-001");
        for (RelationshipEdge rel : relationships) {
            graphStore.saveRelationship(rel);
        }
        log.info("Knowledge Graph initialized with {} entities and {} relationships",
                graphStore.getEntityCount(), graphStore.getRelationshipCount());
    }

    public void processDocumentForGraph(String docId, String title, String body) {
        if (docId == null) return;
        String combinedText = (title != null ? title + " " : "") + (body != null ? body : "");
        List<EntityNode> extracted = entityExtractor.extractEntities(combinedText);

        for (EntityNode entity : extracted) {
            graphStore.saveEntity(entity);
        }

        List<RelationshipEdge> rels = entityExtractor.extractRelationships(extracted, docId);
        for (RelationshipEdge rel : rels) {
            graphStore.saveRelationship(rel);
        }
    }

    public EntityNode getEntityByName(String name) {
        return graphStore.resolveCanonicalEntity(name);
    }

    public List<RelationshipEdge> getEntityNeighbors(String entityId) {
        return graphStore.getEntityNeighbors(entityId);
    }

    public List<EntityNode> searchGraph(String query, int limit) {
        return graphStore.searchEntities(query, limit);
    }

    public Map<String, Object> getGraphStatistics() {
        return Map.of(
                "totalEntities", graphStore.getEntityCount(),
                "totalRelationships", graphStore.getRelationshipCount(),
                "graphStorageEngine", "InMemGraphStore (Pluggable Graph Database)",
                "status", "ACTIVE"
        );
    }
}
