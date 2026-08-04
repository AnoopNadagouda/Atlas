package com.atlas.keywordsearch.pipeline;

import com.atlas.common.dto.PageResponse;
import com.atlas.common.dto.SearchRequest;
import com.atlas.common.dto.SearchResultDto;
import com.atlas.keywordsearch.engine.BM25Ranker;
import com.atlas.keywordsearch.engine.SegmentLookupEngine;
import com.atlas.keywordsearch.engine.SnippetGenerator;
import com.atlas.keywordsearch.planner.QueryPlan;
import com.atlas.keywordsearch.planner.QueryPlannerService;
import com.atlas.keywordsearch.query.ParsedQuery;
import com.atlas.keywordsearch.query.QueryParser;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class SearchPipelineOrchestrator {

    private final QueryParser queryParser;
    private final QueryPlannerService queryPlannerService;
    private final SegmentLookupEngine lookupEngine;
    private final BM25Ranker bm25Ranker;
    private final SnippetGenerator snippetGenerator;

    public PageResponse<SearchResultDto> executePipeline(SearchRequest request) {
        SearchPipelineContext context = SearchPipelineContext.builder()
                .searchRequest(request)
                .build();

        long startTime = System.currentTimeMillis();

        // Stage 1: Parsing Stage
        ParsedQuery parsedQuery = queryParser.parse(request.getQuery());
        context.setParsedQuery(parsedQuery);

        // Stage 2: Planning Stage
        QueryPlan plan = queryPlannerService.plan(parsedQuery);
        context.setQueryPlan(plan);

        // Stage 3: Retrieval Stage (BM25 Index Traversal)
        SegmentLookupEngine.LookupResult lookupResult = lookupEngine.lookup(parsedQuery);
        context.setLookupResult(lookupResult);

        // Stage 4: Ranking Stage (Robertson-Spärck Jones BM25)
        List<BM25Ranker.ScoredDocument> rankedDocs = bm25Ranker.rank(parsedQuery, lookupResult);
        context.setRankedDocuments(rankedDocs);

        // Stage 5: Response Building Stage
        List<SearchResultDto> resultList = new ArrayList<>();
        for (BM25Ranker.ScoredDocument doc : rankedDocs) {
            String title = "Document " + doc.getDocId();
            String dummyBody = "Atlas distributed search engine document content for ID " + doc.getDocId() + " matching terms " + doc.getMatchedTerms();
            String snippet = snippetGenerator.generateSnippet(dummyBody, doc.getMatchedTerms());

            SearchResultDto resultDto = SearchResultDto.builder()
                    .id(doc.getDocId())
                    .url("https://atlas.internal/doc/" + doc.getDocId())
                    .title(title)
                    .snippet(snippet)
                    .score(doc.getBm25Score())
                    .bm25Score(doc.getBm25Score())
                    .matchedTerms(doc.getMatchedTerms())
                    .matchedFields(doc.getMatchedFields())
                    .termContributions(doc.getTermContributions())
                    .build();

            resultList.add(resultDto);
        }

        int totalElements = resultList.size();
        int fromIndex = Math.min(request.getPage() * request.getSize(), totalElements);
        int toIndex = Math.min(fromIndex + request.getSize(), totalElements);

        List<SearchResultDto> pagedContent = resultList.subList(fromIndex, toIndex);
        int totalPages = (int) Math.ceil((double) totalElements / Math.max(1, request.getSize()));

        PageResponse<SearchResultDto> response = PageResponse.<SearchResultDto>builder()
                .content(pagedContent)
                .pageNumber(request.getPage())
                .pageSize(request.getSize())
                .totalElements(totalElements)
                .totalPages(totalPages)
                .last(request.getPage() >= totalPages - 1)
                .build();

        context.setFinalResponse(response);

        log.info("Search Pipeline executed successfully in {} ms via strategy '{}'", System.currentTimeMillis() - startTime, plan.getSelectedStrategy());

        return response;
    }
}
