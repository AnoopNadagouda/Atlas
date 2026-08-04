package com.atlas.keywordsearch.query;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ParsedQuery {

    private String rawQuery;

    @Builder.Default
    private List<String> normalizedTerms = new ArrayList<>();

    @Builder.Default
    private List<List<String>> phrases = new ArrayList<>(); // Quoted phrase token lists

    @Builder.Default
    private List<String> mustTerms = new ArrayList<>(); // AND terms

    @Builder.Default
    private List<String> mustNotTerms = new ArrayList<>(); // NOT terms

    @Builder.Default
    private List<String> shouldTerms = new ArrayList<>(); // OR / standard terms
}
