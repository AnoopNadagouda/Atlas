package com.atlas.keywordsearch.graph;

import com.atlas.domain.graph.EntityNode;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

class KnowledgeGraphServiceTest {

    private KnowledgeGraphService knowledgeGraphService;

    @BeforeEach
    void setUp() {
        InMemGraphStore graphStore = new InMemGraphStore();
        RegexEntityExtractor extractor = new RegexEntityExtractor();
        knowledgeGraphService = new KnowledgeGraphService(graphStore, extractor);
        knowledgeGraphService.initSeedGraph();
    }

    @Test
    void testGraphInitializationAndEntityLookup() {
        EntityNode entity = knowledgeGraphService.getEntityByName("Atlas");

        assertNotNull(entity);
        assertEquals("Atlas", entity.getName());

        Map<String, Object> stats = knowledgeGraphService.getGraphStatistics();
        assertNotNull(stats);
        assertTrue((int) stats.get("totalEntities") >= 5);
    }
}
