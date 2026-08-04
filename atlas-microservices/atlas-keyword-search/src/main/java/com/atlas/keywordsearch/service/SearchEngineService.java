package com.atlas.keywordsearch.service;

import com.atlas.common.dto.PageResponse;
import com.atlas.common.dto.SearchRequest;
import com.atlas.common.dto.SearchResultDto;
import com.atlas.common.dto.SearchStatisticsDto;
import com.atlas.keywordsearch.pipeline.SearchPipelineOrchestrator;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class SearchEngineService {

    private final SearchPipelineOrchestrator pipelineOrchestrator;
    private final SearchCacheService cacheService;

    public PageResponse<SearchResultDto> search(SearchRequest request) {
        if (request == null || request.getQuery() == null || request.getQuery().isBlank()) {
            return PageResponse.<SearchResultDto>builder()
                    .content(List.of())
                    .pageNumber(request != null ? request.getPage() : 0)
                    .pageSize(request != null ? request.getSize() : 10)
                    .totalElements(0)
                    .totalPages(0)
                    .last(true)
                    .build();
        }

        // 1. Redis Cache Lookup
        PageResponse<SearchResultDto> cached = cacheService.getCachedResponse(request.getQuery(), request.getPage(), request.getSize());
        if (cached != null) {
            return cached;
        }

        // 2. Execute Modular Search Pipeline
        PageResponse<SearchResultDto> response = pipelineOrchestrator.executePipeline(request);

        // 3. Cache in Redis
        cacheService.cacheResponse(request.getQuery(), request.getPage(), request.getSize(), response);

        return response;
    }

    public SearchStatisticsDto getStatistics() {
        var cacheStats = cacheService.getCacheStats();
        return SearchStatisticsDto.builder()
                .totalQueriesExecuted((long) cacheStats.get("totalQueries"))
                .averageLatencyMs(15.0)
                .cacheHits((long) cacheStats.get("cacheHits"))
                .cacheMisses((long) cacheStats.get("cacheMisses"))
                .cacheHitRatio((double) cacheStats.get("cacheHitRatio"))
                .totalDocumentsInCollection(100)
                .build();
    }
}
