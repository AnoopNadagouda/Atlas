package com.atlas.keywordsearch.controller;

import com.atlas.common.dto.ApiResponse;
import com.atlas.common.dto.PageResponse;
import com.atlas.common.dto.SearchRequest;
import com.atlas.common.dto.SearchResultDto;
import com.atlas.keywordsearch.config.AtlasFeatureProperties;
import com.atlas.keywordsearch.config.AtlasHybridProperties;
import com.atlas.keywordsearch.hybrid.HybridSearchService;
import com.atlas.keywordsearch.planner.QueryPlannerService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@Slf4j
@RestController
@RequestMapping("/api/v2/search")
@RequiredArgsConstructor
public class HybridSearchController {

    private final HybridSearchService hybridSearchService;
    private final AtlasHybridProperties hybridProperties;
    private final AtlasFeatureProperties featureProperties;
    private final QueryPlannerService queryPlannerService;

    @PostMapping("/hybrid")
    public ResponseEntity<ApiResponse<PageResponse<SearchResultDto>>> hybridSearch(@Valid @RequestBody SearchRequest request) {
        log.info("API v2 Hybrid Search request for query: '{}'", request.getQuery());
        PageResponse<SearchResultDto> response = hybridSearchService.searchHybrid(request);
        return ResponseEntity.ok(ApiResponse.success(response));
    }

    @GetMapping("/hybrid/statistics")
    public ResponseEntity<ApiResponse<Map<String, Object>>> getHybridStatistics() {
        log.info("Fetching hybrid search statistics");
        return ResponseEntity.ok(ApiResponse.success(Map.of(
                "fusionAlgorithm", "Reciprocal Rank Fusion (RRF)",
                "rrfK", hybridProperties.getRrfK(),
                "timeoutMs", hybridProperties.getTimeoutMs(),
                "parallelExecution", true,
                "virtualThreadsEnabled", true
        )));
    }

    @GetMapping("/hybrid/config")
    public ResponseEntity<ApiResponse<Map<String, Object>>> getHybridConfig() {
        log.info("Fetching hybrid search configuration");
        return ResponseEntity.ok(ApiResponse.success(Map.of(
                "rrfK", hybridProperties.getRrfK(),
                "timeoutMs", hybridProperties.getTimeoutMs(),
                "keywordSearchEnabled", featureProperties.isKeywordSearch(),
                "semanticSearchEnabled", featureProperties.isSemanticSearch(),
                "hybridSearchEnabled", featureProperties.isHybridSearch()
        )));
    }

    @GetMapping("/planner")
    public ResponseEntity<ApiResponse<Map<String, Object>>> getPlannerStatus() {
        log.info("Fetching query planner status");
        return ResponseEntity.ok(ApiResponse.success(Map.of(
                "status", "ACTIVE",
                "defaultStrategy", "HYBRID_RRF",
                "activeFeatures", Map.of(
                        "keyword", featureProperties.isKeywordSearch(),
                        "semantic", featureProperties.isSemanticSearch(),
                        "hybrid", featureProperties.isHybridSearch()
                )
        )));
    }
}
