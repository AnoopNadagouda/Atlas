package com.atlas.keywordsearch.hybrid;

import com.atlas.common.dto.PageResponse;
import com.atlas.common.dto.SearchRequest;
import com.atlas.common.dto.SearchResultDto;
import com.atlas.keywordsearch.config.AtlasHybridProperties;
import com.atlas.keywordsearch.pipeline.SearchPipelineOrchestrator;
import com.atlas.keywordsearch.vector.SemanticSearchService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.TimeUnit;

@Slf4j
@Service
@RequiredArgsConstructor
public class HybridSearchService {

    private final SearchPipelineOrchestrator keywordPipelineOrchestrator;
    private final SemanticSearchService semanticSearchService;
    private final ReciprocalRankFusionEngine rrfEngine;
    private final AtlasHybridProperties hybridProperties;

    public PageResponse<SearchResultDto> searchHybrid(SearchRequest request) {
        if (request == null || request.getQuery() == null || request.getQuery().isBlank()) {
            return PageResponse.<SearchResultDto>builder()
                    .content(Collections.emptyList())
                    .pageNumber(0)
                    .pageSize(10)
                    .totalElements(0)
                    .totalPages(0)
                    .last(true)
                    .build();
        }

        long startTime = System.currentTimeMillis();
        log.info("Executing Parallel Hybrid Search for query: '{}'", request.getQuery());

        // Parallel Async Execution via CompletableFuture / Virtual Threads
        CompletableFuture<List<SearchResultDto>> bm25Future = CompletableFuture.supplyAsync(() -> {
            try {
                PageResponse<SearchResultDto> page = keywordPipelineOrchestrator.executePipeline(request);
                return page != null ? page.getContent() : Collections.<SearchResultDto>emptyList();
            } catch (Exception e) {
                log.error("BM25 Retrieval failed during parallel hybrid execution: {}", e.getMessage());
                return Collections.<SearchResultDto>emptyList();
            }
        });

        CompletableFuture<List<SearchResultDto>> semanticFuture = CompletableFuture.supplyAsync(() -> {
            try {
                PageResponse<SearchResultDto> page = semanticSearchService.search(request);
                return page != null ? page.getContent() : Collections.<SearchResultDto>emptyList();
            } catch (Exception e) {
                log.error("Semantic Retrieval failed during parallel hybrid execution: {}", e.getMessage());
                return Collections.<SearchResultDto>emptyList();
            }
        });

        // Wait for both engines to complete
        List<SearchResultDto> bm25Results = Collections.emptyList();
        List<SearchResultDto> semanticResults = Collections.emptyList();

        try {
            CompletableFuture.allOf(bm25Future, semanticFuture).get(hybridProperties.getTimeoutMs(), TimeUnit.MILLISECONDS);
            bm25Results = bm25Future.get();
            semanticResults = semanticFuture.get();
        } catch (Exception e) {
            log.warn("Parallel execution timeout or partial failure: {}", e.getMessage());
            try {
                if (bm25Future.isDone() && !bm25Future.isCompletedExceptionally()) bm25Results = bm25Future.get();
                if (semanticFuture.isDone() && !semanticFuture.isCompletedExceptionally()) semanticResults = semanticFuture.get();
            } catch (Exception ignored) {}
        }

        // Execute Reciprocal Rank Fusion (RRF)
        List<SearchResultDto> fusedResults = rrfEngine.fuse(bm25Results, semanticResults);

        // Paginate Results
        int totalElements = fusedResults.size();
        int fromIndex = Math.min(request.getPage() * request.getSize(), totalElements);
        int toIndex = Math.min(fromIndex + request.getSize(), totalElements);

        List<SearchResultDto> pagedContent = fusedResults.subList(fromIndex, toIndex);
        int totalPages = (int) Math.ceil((double) totalElements / Math.max(1, request.getSize()));

        long executionTime = System.currentTimeMillis() - startTime;
        log.info("Parallel Hybrid Search completed in {} ms, merged total results: {}", executionTime, totalElements);

        return PageResponse.<SearchResultDto>builder()
                .content(pagedContent)
                .pageNumber(request.getPage())
                .pageSize(request.getSize())
                .totalElements(totalElements)
                .totalPages(totalPages)
                .last(request.getPage() >= totalPages - 1)
                .build();
    }
}
