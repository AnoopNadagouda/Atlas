package com.atlas.keywordsearch.controller;

import com.atlas.common.dto.ApiResponse;
import com.atlas.domain.graph.EntityNode;
import com.atlas.domain.graph.RelationshipEdge;
import com.atlas.keywordsearch.graph.EntityAwareSearchService;
import com.atlas.keywordsearch.graph.KnowledgeGraphService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@Slf4j
@RestController
@RequestMapping("/api/v3/graph")
@RequiredArgsConstructor
public class KnowledgeGraphController {

    private final KnowledgeGraphService knowledgeGraphService;
    private final EntityAwareSearchService entityAwareSearchService;

    @PostMapping("/rebuild")
    public ResponseEntity<ApiResponse<String>> rebuildGraph() {
        log.info("Triggering Knowledge Graph rebuild");
        knowledgeGraphService.initSeedGraph();
        return ResponseEntity.ok(ApiResponse.success("Knowledge Graph rebuilt successfully"));
    }

    @GetMapping("/entity/{name}")
    public ResponseEntity<ApiResponse<EntityNode>> getEntity(@PathVariable String name) {
        log.info("Fetching entity details for name: '{}'", name);
        EntityNode node = knowledgeGraphService.getEntityByName(name);
        return ResponseEntity.ok(ApiResponse.success(node));
    }

    @GetMapping("/entity/{id}/neighbors")
    public ResponseEntity<ApiResponse<List<RelationshipEdge>>> getNeighbors(@PathVariable String id) {
        log.info("Fetching neighbors for entity ID: '{}'", id);
        List<RelationshipEdge> neighbors = knowledgeGraphService.getEntityNeighbors(id);
        return ResponseEntity.ok(ApiResponse.success(neighbors));
    }

    @GetMapping("/search")
    public ResponseEntity<ApiResponse<Map<String, Object>>> searchGraph(@RequestParam String query) {
        log.info("Executing entity-aware graph search for query: '{}'", query);
        Map<String, Object> result = entityAwareSearchService.enrichQueryWithGraphContext(query);
        return ResponseEntity.ok(ApiResponse.success(result));
    }

    @GetMapping("/statistics")
    public ResponseEntity<ApiResponse<Map<String, Object>>> getStatistics() {
        log.info("Fetching Knowledge Graph statistics");
        return ResponseEntity.ok(ApiResponse.success(knowledgeGraphService.getGraphStatistics()));
    }
}
