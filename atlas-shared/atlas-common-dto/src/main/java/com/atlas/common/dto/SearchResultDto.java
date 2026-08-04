package com.atlas.common.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Map;
import java.util.Set;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SearchResultDto {
    private String id;
    private String url;
    private String title;
    private String snippet;
    private double score;
    private double bm25Score;
    private double vectorScore;
    private double pageRankScore;
    private String domain;
    private Set<String> matchedTerms;
    private Set<String> matchedFields;
    private Map<String, Double> termContributions;
    private Map<String, Object> metadata;
}
