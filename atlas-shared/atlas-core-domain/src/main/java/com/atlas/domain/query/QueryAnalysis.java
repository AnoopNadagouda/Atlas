package com.atlas.domain.query;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class QueryAnalysis {
    private String rawQuery;
    private String normalizedQuery;
    private String detectedLanguage;
    private String correctedQuery;
    private String rewrittenQuery;
    private List<String> expandedSynonyms;
    private QueryIntent intent;
    private double confidenceScore;
}
