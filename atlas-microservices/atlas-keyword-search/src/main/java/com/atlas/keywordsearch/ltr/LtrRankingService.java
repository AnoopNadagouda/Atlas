package com.atlas.keywordsearch.ltr;

import com.atlas.domain.ltr.LtrFeatureVector;
import com.atlas.domain.ltr.LtrModel;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Map;

@Slf4j
@Service
@RequiredArgsConstructor
public class LtrRankingService {

    private final LtrFeatureExtractor featureExtractor;
    private final LtrModelRegistry modelRegistry;

    public double predictScore(String query, String docId, double bm25, double semantic, double pageRank, double freshness) {
        LtrFeatureVector vector = featureExtractor.extractFeatures(query, docId, bm25, semantic, pageRank, freshness);
        LtrModel model = modelRegistry.getActiveModel();

        if (model == null || model.getFeatureWeights() == null) {
            log.warn("[LtrRankingService] Active LTR model unavailable. Falling back to multi-signal ranking.");
            return (bm25 * 0.4) + (semantic * 0.4) + (pageRank * 0.2);
        }

        double score = 0.0;
        Map<String, Double> weights = model.getFeatureWeights();
        for (Map.Entry<String, Double> entry : vector.getNormalizedFeatures().entrySet()) {
            double weight = weights.getOrDefault(entry.getKey(), 0.0);
            score += entry.getValue() * weight;
        }
        return score;
    }
}
