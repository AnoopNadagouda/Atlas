package com.atlas.common.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Map;

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
    private Map<String, Object> metadata;
}
