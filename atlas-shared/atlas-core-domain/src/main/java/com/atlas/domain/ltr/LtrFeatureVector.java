package com.atlas.domain.ltr;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Map;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class LtrFeatureVector {
    private String docId;
    private String query;
    private double bm25Score;
    private double semanticScore;
    private double pageRankScore;
    private double freshnessScore;
    private double ctrScore;
    private double entityMatchScore;
    private Map<String, Double> normalizedFeatures;
}
