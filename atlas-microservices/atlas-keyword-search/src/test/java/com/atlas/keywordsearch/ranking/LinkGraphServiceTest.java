package com.atlas.keywordsearch.ranking;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class LinkGraphServiceTest {

    private LinkGraphService linkGraphService;

    @BeforeEach
    void setUp() {
        linkGraphService = new LinkGraphService();
        linkGraphService.initSeedGraph();
    }

    @Test
    void testLinkGraphTopology() {
        assertTrue(linkGraphService.getNodeIds().size() >= 3);
        assertTrue(linkGraphService.getEdgeCount() >= 4);

        List<String> outgoing = linkGraphService.getOutgoingLinks("doc-foundation-001");
        assertFalse(outgoing.isEmpty());
    }
}
