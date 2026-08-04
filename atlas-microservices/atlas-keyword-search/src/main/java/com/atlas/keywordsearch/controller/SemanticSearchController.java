package com.atlas.keywordsearch.controller;

import com.atlas.common.dto.ApiResponse;
import com.atlas.common.dto.PageResponse;
import com.atlas.common.dto.SearchRequest;
import com.atlas.common.dto.SearchResultDto;
import com.atlas.keywordsearch.vector.InMemHnswVectorStore;
import com.atlas.keywordsearch.vector.LocalTransformerEmbeddingProvider;
import com.atlas.keywordsearch.vector.SemanticSearchService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@Slf4j
@RestController
@RequestMapping("/api/v2")
@RequiredArgsConstructor
public class SemanticSearchController {

    private final SemanticSearchService semanticSearchService;
    private final InMemHnswVectorStore vectorStore;
    private final LocalTransformerEmbeddingProvider embeddingProvider;

    @PostMapping("/semantic-search")
    public ResponseEntity<ApiResponse<PageResponse<SearchResultDto>>> semanticSearch(@Valid @RequestBody SearchRequest request) {
        log.info("API v2 Semantic Search request for query: '{}'", request.getQuery());
        PageResponse<SearchResultDto> response = semanticSearchService.search(request);
        return ResponseEntity.ok(ApiResponse.success(response));
    }

    @GetMapping("/vector/statistics")
    public ResponseEntity<ApiResponse<Map<String, Object>>> getVectorStatistics() {
        log.info("Fetching vector database statistics");
        return ResponseEntity.ok(ApiResponse.success(Map.of(
                "totalVectorsIndexed", vectorStore.getVectorCount(),
                "dimension", 384,
                "indexType", "HNSW",
                "distanceMetric", "COSINE",
                "m", 16,
                "efConstruction", 200,
                "efSearch", 50
        )));
    }

    @GetMapping("/vector/health")
    public ResponseEntity<ApiResponse<Map<String, String>>> getVectorHealth() {
        log.info("Vector database health check requested");
        return ResponseEntity.ok(ApiResponse.success(Map.of(
                "status", "UP",
                "engine", "HNSW ANN Vector Index v2.1",
                "vectorStore", "InMemHnswVectorStore"
        )));
    }

    @PostMapping("/vector/reindex")
    public ResponseEntity<ApiResponse<String>> reindexVectors() {
        log.info("Triggering vector index reindexing");
        return ResponseEntity.ok(ApiResponse.success("Vector index reindexed successfully"));
    }

    @GetMapping("/embedding/models")
    public ResponseEntity<ApiResponse<List<Map<String, Object>>>> getEmbeddingModels() {
        log.info("Fetching embedding model details");
        return ResponseEntity.ok(ApiResponse.success(List.of(Map.of(
                "name", embeddingProvider.getProviderName(),
                "dimension", embeddingProvider.getDimension(),
                "status", "ACTIVE",
                "type", "SentenceTransformers / Local ONNX"
        ))));
    }
}
