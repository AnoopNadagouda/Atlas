package com.atlas.keywordsearch.ranking;

import com.atlas.domain.ranking.PageRankScore;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

class PageRankEngineTest {

    private PageRankEngine pageRankEngine;

    @BeforeEach
    void setUp() {
        LinkGraphService linkGraph = new LinkGraphService();
        linkGraph.initSeedGraph();
        pageRankEngine = new PageRankEngine(linkGraph);
    }

    @Test
    void testPageRankConvergence() {
        Map<String, PageRankScore> scores = pageRankEngine.runPageRank();
        assertNotNull(scores);
        assertFalse(scores.isEmpty());

        PageRankScore score = pageRankEngine.getScore("doc-foundation-001");
        assertNotNull(score);
        assertTrue(score.getCurrentScore() > 0.0);
    }
}
