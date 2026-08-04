package com.atlas.keywordsearch.query;

import com.atlas.domain.query.QueryAnalysis;
import com.atlas.domain.query.QueryIntent;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class QueryIntelligencePipelineTest {

    private QueryIntelligencePipeline pipeline;

    @BeforeEach
    void setUp() {
        SpellCheckService spellCheck = new SpellCheckService();
        QueryRewriteService rewrite = new QueryRewriteService();
        QueryIntentClassifier classifier = new QueryIntentClassifier();

        pipeline = new QueryIntelligencePipeline(spellCheck, rewrite, classifier);
    }

    @Test
    void testFullQueryAnalysisPipeline() {
        QueryAnalysis analysis = pipeline.analyzeQuery("atls ai kafka");
        assertNotNull(analysis);
        assertEquals("atlas artificial intelligence apache kafka", analysis.getRewrittenQuery());
        assertEquals(QueryIntent.ENTITY_LOOKUP, analysis.getIntent());
    }
}
