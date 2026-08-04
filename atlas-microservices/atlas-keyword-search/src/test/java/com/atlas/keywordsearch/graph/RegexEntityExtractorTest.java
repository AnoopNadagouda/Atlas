package com.atlas.keywordsearch.graph;

import com.atlas.domain.graph.EntityNode;
import com.atlas.domain.graph.RelationshipEdge;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class RegexEntityExtractorTest {

    private RegexEntityExtractor extractor;

    @BeforeEach
    void setUp() {
        extractor = new RegexEntityExtractor();
    }

    @Test
    void testEntityAndRelationshipExtraction() {
        String text = "Atlas enterprise search platform built with Spring Boot, Apache Kafka, Redis, PostgreSQL, and Java 21.";
        List<EntityNode> entities = extractor.extractEntities(text);

        assertNotNull(entities);
        assertTrue(entities.size() >= 4);

        List<RelationshipEdge> rels = extractor.extractRelationships(entities, "doc-test-1");
        assertNotNull(rels);
        assertFalse(rels.isEmpty());
    }
}
