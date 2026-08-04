package com.atlas.keywordsearch.graph;

import com.atlas.domain.graph.EntityNode;
import com.atlas.domain.graph.EntityType;
import com.atlas.domain.graph.RelationType;
import com.atlas.domain.graph.RelationshipEdge;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

class InMemGraphStoreTest {

    private InMemGraphStore graphStore;

    @BeforeEach
    void setUp() {
        graphStore = new InMemGraphStore();
    }

    @Test
    void testEntityStorageAliasResolutionAndNeighborTraversal() {
        EntityNode node1 = EntityNode.builder()
                .id("node-1")
                .name("Spring Boot")
                .canonicalName("Spring Boot Framework")
                .type(EntityType.FRAMEWORK)
                .aliases(Set.of("spring"))
                .build();

        EntityNode node2 = EntityNode.builder()
                .id("node-2")
                .name("Java")
                .canonicalName("Java Language")
                .type(EntityType.PROGRAMMING_LANGUAGE)
                .build();

        graphStore.saveEntity(node1);
        graphStore.saveEntity(node2);

        assertEquals(2, graphStore.getEntityCount());

        // Alias resolution
        EntityNode resolved = graphStore.resolveCanonicalEntity("spring");
        assertNotNull(resolved);
        assertEquals("node-1", resolved.getId());

        // Relationship traversal
        RelationshipEdge rel = RelationshipEdge.builder()
                .id("rel-1")
                .sourceEntityId("node-1")
                .targetEntityId("node-2")
                .relationType(RelationType.DEPENDS_ON)
                .build();

        graphStore.saveRelationship(rel);
        List<RelationshipEdge> neighbors = graphStore.getEntityNeighbors("node-1");

        assertFalse(neighbors.isEmpty());
        assertEquals("rel-1", neighbors.get(0).getId());
    }
}
