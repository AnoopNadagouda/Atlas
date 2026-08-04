package com.atlas.keywordsearch.pipeline;

import com.atlas.common.dto.PageResponse;
import com.atlas.common.dto.SearchRequest;
import com.atlas.common.dto.SearchResultDto;
import com.atlas.keywordsearch.engine.BM25Ranker;
import com.atlas.keywordsearch.engine.SegmentLookupEngine;
import com.atlas.keywordsearch.planner.QueryPlan;
import com.atlas.keywordsearch.query.ParsedQuery;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SearchPipelineContext {

    private SearchRequest searchRequest;

    private ParsedQuery parsedQuery;

    private QueryPlan queryPlan;

    private SegmentLookupEngine.LookupResult lookupResult;

    private List<BM25Ranker.ScoredDocument> rankedDocuments;

    private PageResponse<SearchResultDto> finalResponse;
}
