package com.atlas.keywordsearch.ltr;

import com.atlas.domain.ltr.LtrFeatureVector;
import org.springframework.stereotype.Service;

import java.util.LinkedHashMap;
import java.util.Map;

@Service
public class LtrFeatureExtractor {

    public LtrFeatureVector extractFeatures(String query, String docId, double bm25, double semantic, double pageRank, double freshness) {
        Map<String, Double> norm = new LinkedHashMap<>();
        norm.put("bm25_norm", Math.min(1.0, bm25));
        norm.put("semantic_norm", Math.min(1.0, semantic));
        norm.put("pageRank_norm", Math.min(1.0, pageRank * 2.0));
        norm.put("freshness_norm", Math.min(1.0, freshness));
        norm.put("ctr_norm", 0.425);
        norm.put("entityMatch_norm", 0.95);

        return LtrFeatureVector.builder()
                .docId(docId)
                .query(query)
                .bm25Score(bm25)
                .semanticScore(semantic)
                .pageRankScore(pageRank)
                .freshnessScore(freshness)
                .ctrScore(0.425)
                .entityMatchScore(0.95)
                .normalizedFeatures(norm)
                .build();
    }
}
