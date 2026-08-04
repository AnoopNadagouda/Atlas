package com.atlas.keywordsearch.hybrid;

import com.atlas.common.dto.SearchResultDto;
import com.atlas.keywordsearch.config.AtlasHybridProperties;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

class ReciprocalRankFusionEngineTest {

    private ReciprocalRankFusionEngine rrfEngine;

    @BeforeEach
    void setUp() {
        AtlasHybridProperties properties = new AtlasHybridProperties();
        properties.setRrfK(60);
        rrfEngine = new ReciprocalRankFusionEngine(properties);
    }

    @Test
    void testReciprocalRankFusionAndDeduplication() {
        SearchResultDto b1 = SearchResultDto.builder()
                .id("doc-1")
                .title("Doc 1")
                .url("https://atlas.internal/1")
                .bm25Score(0.9)
                .matchedTerms(Set.of("atlas"))
                .build();

        SearchResultDto b2 = SearchResultDto.builder()
                .id("doc-2")
                .title("Doc 2")
                .url("https://atlas.internal/2")
                .bm25Score(0.7)
                .matchedTerms(Set.of("search"))
                .build();

        SearchResultDto s1 = SearchResultDto.builder()
                .id("doc-2")
                .title("Doc 2")
                .url("https://atlas.internal/2")
                .vectorScore(0.95)
                .build();

        SearchResultDto s2 = SearchResultDto.builder()
                .id("doc-3")
                .title("Doc 3")
                .url("https://atlas.internal/3")
                .vectorScore(0.85)
                .build();

        List<SearchResultDto> fused = rrfEngine.fuse(List.of(b1, b2), List.of(s1, s2));

        assertNotNull(fused);
        assertEquals(3, fused.size()); // 3 unique docs

        // doc-2 was rank #2 in BM25 (1/62 = 0.0161) and rank #1 in Semantic (1/61 = 0.0163) => sum = 0.0325
        SearchResultDto topDoc = fused.get(0);
        assertEquals("doc-2", topDoc.getId());
        assertEquals(1, topDoc.getFinalRank());
        assertTrue(topDoc.getRetrievalSources().contains("HYBRID"));
        assertEquals(0.95, topDoc.getVectorScore());
        assertEquals(0.7, topDoc.getBm25Score());
    }
}
