package com.atlas.keywordsearch.planner;

import com.atlas.keywordsearch.config.AtlasFeatureProperties;
import com.atlas.keywordsearch.query.ParsedQuery;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class QueryPlannerServiceTest {

    private QueryPlannerService queryPlannerService;
    private AtlasFeatureProperties featureProperties;

    @BeforeEach
    void setUp() {
        featureProperties = new AtlasFeatureProperties();
        queryPlannerService = new QueryPlannerService(featureProperties);
    }

    @Test
    void testDefaultKeywordStrategy() {
        featureProperties.setHybridSearch(false);
        featureProperties.setSemanticSearch(false);

        ParsedQuery query = ParsedQuery.builder().rawQuery("atlas search").build();
        QueryPlan plan = queryPlannerService.plan(query);

        assertNotNull(plan);
        assertEquals(RetrievalStrategy.KEYWORD_BM25, plan.getSelectedStrategy());
    }

    @Test
    void testHybridStrategyWhenFeatureFlagEnabled() {
        featureProperties.setHybridSearch(true);
        ParsedQuery query = ParsedQuery.builder().rawQuery("atlas search").build();
        QueryPlan plan = queryPlannerService.plan(query);

        assertNotNull(plan);
        assertEquals(RetrievalStrategy.HYBRID_RRF, plan.getSelectedStrategy());
        assertTrue(plan.getActiveFeatures().contains("HYBRID_SEARCH"));
    }
}
