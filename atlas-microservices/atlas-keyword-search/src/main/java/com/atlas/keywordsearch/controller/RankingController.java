package com.atlas.keywordsearch.controller;

import com.atlas.common.dto.ApiResponse;
import com.atlas.domain.ranking.PageRankScore;
import com.atlas.keywordsearch.ranking.PageRankEngine;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@Slf4j
@RestController
@RequestMapping("/api/v6/ranking")
@RequiredArgsConstructor
public class RankingController {

    private final PageRankEngine pageRankEngine;

    @PostMapping("/pagerank/run")
    public ResponseEntity<ApiResponse<Map<String, PageRankScore>>> runPageRank() {
        log.info("Triggering PageRank calculation run");
        Map<String, PageRankScore> scores = pageRankEngine.runPageRank();
        return ResponseEntity.ok(ApiResponse.success(scores));
    }

    @GetMapping("/pagerank/status")
    public ResponseEntity<ApiResponse<Map<String, Object>>> getPageRankStatus() {
        log.info("Fetching PageRank engine status");
        return ResponseEntity.ok(ApiResponse.success(pageRankEngine.getStatistics()));
    }

    @GetMapping("/statistics")
    public ResponseEntity<ApiResponse<Map<String, Object>>> getStatistics() {
        log.info("Fetching Ranking Pipeline statistics");
        Map<String, Object> stats = Map.of(
                "pageRankStats", pageRankEngine.getStatistics(),
                "rankingWeights", Map.of("RRF", 0.40, "PageRank", 0.35, "Freshness", 0.25),
                "status", "ACTIVE"
        );
        return ResponseEntity.ok(ApiResponse.success(stats));
    }

    @GetMapping("/document/{id}")
    public ResponseEntity<ApiResponse<PageRankScore>> getDocumentRank(@PathVariable String id) {
        log.info("Fetching PageRank details for doc ID: '{}'", id);
        return ResponseEntity.ok(ApiResponse.success(pageRankEngine.getScore(id)));
    }
}
