package com.atlas.keywordsearch.engine;

import com.atlas.keywordsearch.config.SearchProperties;
import com.atlas.keywordsearch.query.ParsedQuery;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Map;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

class BM25RankerTest {

    private BM25Ranker ranker;

    @BeforeEach
    void setUp() {
        SearchProperties properties = new SearchProperties();
        ranker = new BM25Ranker(properties);
    }

    @Test
    void testBM25ScoringAndRanking() {
        ParsedQuery query = ParsedQuery.builder()
                .rawQuery("atlas search")
                .normalizedTerms(List.of("atlas", "search"))
                .build();

        SegmentLookupEngine.PostingEntry p1 = SegmentLookupEngine.PostingEntry.builder()
                .docId("doc-1")
                .termFrequency(3)
                .fieldFlags(Set.of("TITLE"))
                .build();

        SegmentLookupEngine.PostingEntry p2 = SegmentLookupEngine.PostingEntry.builder()
                .docId("doc-2")
                .termFrequency(1)
                .fieldFlags(Set.of("BODY"))
                .build();

        SegmentLookupEngine.LookupResult lookupResult = SegmentLookupEngine.LookupResult.builder()
                .termDocPostings(Map.of("atlas", Map.of("doc-1", p1, "doc-2", p2)))
                .termDocFrequencies(Map.of("atlas", 2))
                .totalCollectionDocs(2)
                .averageDocLength(100.0)
                .docLengths(Map.of("doc-1", 100, "doc-2", 100))
                .build();

        List<BM25Ranker.ScoredDocument> ranked = ranker.rank(query, lookupResult);

        assertFalse(ranked.isEmpty());
        assertEquals("doc-1", ranked.get(0).getDocId());
        assertTrue(ranked.get(0).getBm25Score() > ranked.get(1).getBm25Score());
    }
}
