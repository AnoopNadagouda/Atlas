package com.atlas.keywordsearch.planner;

import com.atlas.keywordsearch.query.ParsedQuery;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class QueryPlan {
    private ParsedQuery parsedQuery;
    private RetrievalStrategy selectedStrategy;
    private String intentCategory;
    private Set<String> activeFeatures;
}
