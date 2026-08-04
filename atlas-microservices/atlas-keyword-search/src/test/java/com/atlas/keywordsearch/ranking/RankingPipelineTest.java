package com.atlas.keywordsearch.ranking;

import com.atlas.common.dto.SearchResultDto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class RankingPipelineTest {

    private RankingPipeline rankingPipeline;

    @BeforeEach
    void setUp() {
        LinkGraphService linkGraph = new LinkGraphService();
        linkGraph.initSeedGraph();
        PageRankEngine pageRankEngine = new PageRankEngine(linkGraph);
        pageRankEngine.runPageRank();
        FreshnessScorer freshnessScorer = new FreshnessScorer();

        rankingPipeline = new RankingPipeline(pageRankEngine, freshnessScorer);
    }

    @Test
    void testRankingPipelineFusion() {
        SearchResultDto res1 = SearchResultDto.builder().id("doc-foundation-001").score(0.95).build();
        SearchResultDto res2 = SearchResultDto.builder().id("doc-bm25-002").score(0.80).build();

        List<SearchResultDto> initial = new ArrayList<>(List.of(res1, res2));
        List<SearchResultDto> ranked = rankingPipeline.applyRankingPipeline(initial);

        assertNotNull(ranked);
        assertEquals(2, ranked.size());
        assertTrue(ranked.get(0).getScore() > 0.0);
    }
}
