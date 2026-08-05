package com.atlas.keywordsearch.controller;

import com.atlas.common.dto.ApiResponse;
import com.atlas.domain.connector.FederatedSearchRequest;
import com.atlas.keywordsearch.connector.FederatedSearchService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

/**
 * Production REST API Controller for Enterprise Federated Search Engine.
 * Hosted under /api/v17/search with 100% backward compatibility.
 */
@Slf4j
@RestController
@RequestMapping("/api/v17/search")
@RequiredArgsConstructor
public class FederatedSearchController {

    private final FederatedSearchService federatedSearchService;

    @PostMapping("/federated")
    public ResponseEntity<ApiResponse<Map<String, Object>>> searchFederated(@RequestBody FederatedSearchRequest request) {
        log.info("Executing Enterprise Federated Search request for query: '{}'", request.getQuery());
        Map<String, Object> result = federatedSearchService.executeFederatedSearch(request);
        return ResponseEntity.ok(ApiResponse.success(result));
    }
}
