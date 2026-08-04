package com.atlas.keywordsearch.graph;

import com.atlas.domain.graph.EntityNode;
import com.atlas.domain.graph.EntityType;
import com.atlas.domain.graph.RelationType;
import com.atlas.domain.graph.RelationshipEdge;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.*;

@Slf4j
@Component
public class RegexEntityExtractor {

    private static final Map<String, EntityNode> SEED_ENTITIES = new HashMap<>();

    static {
        SEED_ENTITIES.put("atlas", EntityNode.builder()
                .id("entity-atlas")
                .name("Atlas")
                .canonicalName("Atlas Search Engine")
                .type(EntityType.PRODUCT)
                .aliases(Set.of("atlas search", "atlas platform"))
                .confidenceScore(0.99)
                .attributes(Map.of("category", "Distributed Search Platform"))
                .build());

        SEED_ENTITIES.put("spring boot", EntityNode.builder()
                .id("entity-spring-boot")
                .name("Spring Boot")
                .canonicalName("Spring Boot Framework")
                .type(EntityType.FRAMEWORK)
                .aliases(Set.of("spring", "spring-boot"))
                .confidenceScore(0.98)
                .attributes(Map.of("category", "Java Framework"))
                .build());

        SEED_ENTITIES.put("kafka", EntityNode.builder()
                .id("entity-kafka")
                .name("Apache Kafka")
                .canonicalName("Apache Kafka")
                .type(EntityType.TECHNOLOGY)
                .aliases(Set.of("apache kafka", "kafka messaging"))
                .confidenceScore(0.98)
                .attributes(Map.of("category", "Event Streaming Platform"))
                .build());

        SEED_ENTITIES.put("redis", EntityNode.builder()
                .id("entity-redis")
                .name("Redis")
                .canonicalName("Redis Memory Store")
                .type(EntityType.DATABASE)
                .aliases(Set.of("redis cache"))
                .confidenceScore(0.97)
                .attributes(Map.of("category", "In-Memory Cache"))
                .build());

        SEED_ENTITIES.put("postgresql", EntityNode.builder()
                .id("entity-postgresql")
                .name("PostgreSQL")
                .canonicalName("PostgreSQL Database")
                .type(EntityType.DATABASE)
                .aliases(Set.of("postgres", "pgvector"))
                .confidenceScore(0.97)
                .attributes(Map.of("category", "Relational Database"))
                .build());

        SEED_ENTITIES.put("java", EntityNode.builder()
                .id("entity-java")
                .name("Java")
                .canonicalName("Java Programming Language")
                .type(EntityType.PROGRAMMING_LANGUAGE)
                .aliases(Set.of("java 21", "jdk"))
                .confidenceScore(0.99)
                .attributes(Map.of("category", "Programming Language"))
                .build());
    }

    public List<EntityNode> extractEntities(String text) {
        if (text == null || text.isBlank()) return Collections.emptyList();
        String lower = text.toLowerCase();

        List<EntityNode> extracted = new ArrayList<>();
        for (Map.Entry<String, EntityNode> entry : SEED_ENTITIES.entrySet()) {
            if (lower.contains(entry.getKey())) {
                extracted.add(entry.getValue());
            }
        }
        return extracted;
    }

    public List<RelationshipEdge> extractRelationships(List<EntityNode> extractedEntities, String sourceDocId) {
        if (extractedEntities == null || extractedEntities.size() < 2) return Collections.emptyList();

        List<RelationshipEdge> rels = new ArrayList<>();
        boolean containsAtlas = extractedEntities.stream().anyMatch(e -> e.getId().equals("entity-atlas"));

        if (containsAtlas) {
            for (EntityNode entity : extractedEntities) {
                if (!entity.getId().equals("entity-atlas")) {
                    rels.add(RelationshipEdge.builder()
                            .id(UUID.randomUUID().toString())
                            .sourceEntityId("entity-atlas")
                            .targetEntityId(entity.getId())
                            .relationType(RelationType.USES)
                            .confidenceScore(0.95)
                            .sourceDocumentId(sourceDocId)
                            .metadata(Map.of("extractedBy", "RegexEntityExtractor"))
                            .build());
                }
            }
        }
        return rels;
    }
}
