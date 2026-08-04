package com.atlas.keywordsearch.planner;

import com.atlas.keywordsearch.config.AtlasFeatureProperties;
import com.atlas.keywordsearch.query.ParsedQuery;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;

@Slf4j
@Service
@RequiredArgsConstructor
public class QueryPlannerService {

    private final AtlasFeatureProperties featureProperties;

    public QueryPlan plan(ParsedQuery parsedQuery) {
        Set<String> activeFeatures = new HashSet<>();
        RetrievalStrategy strategy = RetrievalStrategy.KEYWORD_BM25; // Default Phase 1.5 strategy

        if (featureProperties.isHybridSearch()) {
            strategy = RetrievalStrategy.HYBRID_RRF;
            activeFeatures.add("HYBRID_SEARCH");
        } else if (featureProperties.isSemanticSearch()) {
            strategy = RetrievalStrategy.SEMANTIC_VECTOR;
            activeFeatures.add("SEMANTIC_SEARCH");
        }

        if (featureProperties.isAiCopilot()) activeFeatures.add("AI_COPILOT");
        if (featureProperties.isKnowledgeGraph()) activeFeatures.add("KNOWLEDGE_GRAPH");

        log.info("Query Planner determined strategy '{}' for query: '{}'", strategy, parsedQuery.getRawQuery());

        return QueryPlan.builder()
                .parsedQuery(parsedQuery)
                .selectedStrategy(strategy)
                .intentCategory("KEYWORD_INFORMATIONAL")
                .activeFeatures(activeFeatures)
                .build();
    }
}
