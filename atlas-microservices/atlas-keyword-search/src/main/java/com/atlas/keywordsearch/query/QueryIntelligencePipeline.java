package com.atlas.keywordsearch.query;

import com.atlas.domain.query.QueryAnalysis;
import com.atlas.domain.query.QueryIntent;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Slf4j
@Service
@RequiredArgsConstructor
public class QueryIntelligencePipeline {

    private final SpellCheckService spellCheckService;
    private final QueryRewriteService queryRewriteService;
    private final QueryIntentClassifier intentClassifier;

    public QueryAnalysis analyzeQuery(String rawQuery) {
        if (rawQuery == null || rawQuery.isBlank()) {
            return QueryAnalysis.builder()
                    .rawQuery(rawQuery)
                    .normalizedQuery("")
                    .detectedLanguage("en")
                    .correctedQuery("")
                    .rewrittenQuery("")
                    .expandedSynonyms(new ArrayList<>())
                    .intent(QueryIntent.INFORMATIONAL)
                    .confidenceScore(1.0)
                    .build();
        }

        String normalized = rawQuery.trim().toLowerCase();
        String corrected = spellCheckService.correctSpelling(normalized);
        String rewritten = queryRewriteService.rewriteQuery(corrected);
        QueryIntent intent = intentClassifier.classifyIntent(rawQuery);

        log.info("[QueryIntelligencePipeline] Processed query '{}' -> Corrected: '{}', Rewritten: '{}', Intent: {}",
                rawQuery, corrected, rewritten, intent);

        return QueryAnalysis.builder()
                .rawQuery(rawQuery)
                .normalizedQuery(normalized)
                .detectedLanguage("en")
                .correctedQuery(corrected)
                .rewrittenQuery(rewritten)
                .expandedSynonyms(queryRewriteService.getSynonyms(normalized))
                .intent(intent)
                .confidenceScore(0.98)
                .build();
    }
}
