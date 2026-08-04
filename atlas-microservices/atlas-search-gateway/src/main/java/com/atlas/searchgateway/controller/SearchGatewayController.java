package com.atlas.searchgateway.controller;

import com.atlas.common.dto.ApiResponse;
import com.atlas.common.dto.SearchRequest;
import com.atlas.common.dto.SearchResponse;
import com.atlas.common.dto.SearchResultDto;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/api/v1/search")
public class SearchGatewayController {

    @PostMapping("/query")
    public ResponseEntity<ApiResponse<SearchResponse>> executeQuery(@Valid @RequestBody SearchRequest request) {
        log.info("Received search query: '{}', intent: {}", request.getQuery(), request.getIntent());
        
        // Foundation placeholder response - Phase 1.1 milestone contract
        SearchResponse response = SearchResponse.builder()
                .query(request.getQuery())
                .totalHits(1)
                .executionTimeMs(12)
                .cached(false)
                .results(List.of(
                        SearchResultDto.builder()
                                .id("doc-foundation-001")
                                .url("https://atlas.search/docs/foundation")
                                .title("Atlas Platform - Infrastructure Foundation Ready")
                                .snippet("Phase 1.1 Infrastructure & Project Foundation is active and connected.")
                                .score(0.99)
                                .bm25Score(1.25)
                                .domain("atlas.search")
                                .build()
                ))
                .build();

        return ResponseEntity.ok(ApiResponse.success("Search query processed successfully", response));
    }

    @GetMapping("/suggest")
    public ResponseEntity<ApiResponse<List<String>>> getSuggestions(@RequestParam String prefix) {
        log.info("Autocomplete suggestion request for prefix: '{}'", prefix);
        List<String> suggestions = List.of(
                prefix + " search engine",
                prefix + " distributed architecture",
                prefix + " spring boot microservices"
        );
        return ResponseEntity.ok(ApiResponse.success(suggestions));
    }
}
