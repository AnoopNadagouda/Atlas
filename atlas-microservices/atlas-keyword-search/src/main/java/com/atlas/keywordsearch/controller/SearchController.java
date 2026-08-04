package com.atlas.keywordsearch.controller;

import com.atlas.common.dto.ApiResponse;
import com.atlas.common.dto.PageResponse;
import com.atlas.common.dto.SearchRequest;
import com.atlas.common.dto.SearchResultDto;
import com.atlas.common.dto.SearchStatisticsDto;
import com.atlas.keywordsearch.service.SearchCacheService;
import com.atlas.keywordsearch.service.SearchEngineService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@Slf4j
@RestController
@RequestMapping("/api/v1/search")
@RequiredArgsConstructor
public class SearchController {

    private final SearchEngineService searchEngineService;
    private final SearchCacheService cacheService;

    @GetMapping
    public ResponseEntity<ApiResponse<PageResponse<SearchResultDto>>> searchGet(
            @RequestParam(name = "q", required = false, defaultValue = "") String query,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        log.info("GET search query: '{}', page: {}, size: {}", query, page, size);
        SearchRequest request = SearchRequest.builder()
                .query(query)
                .page(page)
                .size(size)
                .build();

        PageResponse<SearchResultDto> response = searchEngineService.search(request);
        return ResponseEntity.ok(ApiResponse.success(response));
    }

    @GetMapping("/{query}")
    public ResponseEntity<ApiResponse<PageResponse<SearchResultDto>>> searchByPath(
            @PathVariable String query,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        log.info("Path search query: '{}'", query);
        SearchRequest request = SearchRequest.builder()
                .query(query)
                .page(page)
                .size(size)
                .build();

        PageResponse<SearchResultDto> response = searchEngineService.search(request);
        return ResponseEntity.ok(ApiResponse.success(response));
    }

    @PostMapping("/query")
    public ResponseEntity<ApiResponse<PageResponse<SearchResultDto>>> searchPost(@Valid @RequestBody SearchRequest request) {
        log.info("POST search request for query: '{}'", request.getQuery());
        PageResponse<SearchResultDto> response = searchEngineService.search(request);
        return ResponseEntity.ok(ApiResponse.success(response));
    }

    @GetMapping("/statistics")
    public ResponseEntity<ApiResponse<SearchStatisticsDto>> getStatistics() {
        log.info("Fetching search statistics");
        SearchStatisticsDto stats = searchEngineService.getStatistics();
        return ResponseEntity.ok(ApiResponse.success(stats));
    }

    @GetMapping("/cache")
    public ResponseEntity<ApiResponse<Map<String, Object>>> getCacheStats() {
        log.info("Fetching search cache statistics");
        Map<String, Object> stats = cacheService.getCacheStats();
        return ResponseEntity.ok(ApiResponse.success(stats));
    }

    @DeleteMapping("/cache")
    public ResponseEntity<ApiResponse<String>> clearCache() {
        log.info("Clearing search cache");
        cacheService.clearCache();
        return ResponseEntity.ok(ApiResponse.success("Search cache cleared successfully"));
    }
}
