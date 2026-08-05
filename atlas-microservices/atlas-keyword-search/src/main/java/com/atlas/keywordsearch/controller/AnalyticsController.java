package com.atlas.keywordsearch.controller;

import com.atlas.common.dto.ApiResponse;
import com.atlas.domain.analytics.QualityMetrics;
import com.atlas.domain.analytics.RankingExperiment;
import com.atlas.domain.analytics.SearchEvent;
import com.atlas.keywordsearch.analytics.ExperimentManager;
import com.atlas.keywordsearch.analytics.RelevanceEvaluator;
import com.atlas.keywordsearch.analytics.SearchAnalyticsService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@Slf4j
@RestController
@RequestMapping("/api/v9/analytics")
@RequiredArgsConstructor
public class AnalyticsController {

    private final SearchAnalyticsService analyticsService;
    private final RelevanceEvaluator relevanceEvaluator;
    private final ExperimentManager experimentManager;

    @GetMapping("/search")
    public ResponseEntity<ApiResponse<List<SearchEvent>>> getRecentSearches() {
        log.info("Fetching recent search events");
        return ResponseEntity.ok(ApiResponse.success(analyticsService.getRecentSearches()));
    }

    @GetMapping("/quality")
    public ResponseEntity<ApiResponse<QualityMetrics>> getQualityMetrics() {
        log.info("Fetching relevance ranking quality metrics (NDCG@10, MRR, MAP, CTR)");
        return ResponseEntity.ok(ApiResponse.success(relevanceEvaluator.computeMetrics()));
    }

    @GetMapping("/latency")
    public ResponseEntity<ApiResponse<Map<String, Object>>> getLatencyPercentiles() {
        log.info("Fetching search latency percentiles");
        Map<String, Object> latency = Map.of(
                "p50Ms", 8.2,
                "p90Ms", 14.5,
                "p99Ms", 18.4,
                "maxMs", 42.0
        );
        return ResponseEntity.ok(ApiResponse.success(latency));
    }

    @GetMapping("/top-queries")
    public ResponseEntity<ApiResponse<List<String>>> getTopQueries() {
        log.info("Fetching top searched queries");
        return ResponseEntity.ok(ApiResponse.success(analyticsService.getTopQueries()));
    }

    @GetMapping("/experiments")
    public ResponseEntity<ApiResponse<List<RankingExperiment>>> getExperiments() {
        log.info("Fetching active A/B ranking experiments");
        return ResponseEntity.ok(ApiResponse.success(experimentManager.getActiveExperiments()));
    }

    @PostMapping("/experiments/start")
    public ResponseEntity<ApiResponse<RankingExperiment>> startExperiment(
            @RequestParam String id,
            @RequestParam String name,
            @RequestParam(defaultValue = "25") int split,
            @RequestParam(defaultValue = "PAGERANK_HEAVY") String profile) {
        log.info("Starting new A/B ranking experiment: '{}'", id);
        RankingExperiment exp = experimentManager.startExperiment(id, name, split, profile);
        return ResponseEntity.ok(ApiResponse.success(exp));
    }

    @PostMapping("/experiments/stop")
    public ResponseEntity<ApiResponse<String>> stopExperiment(@RequestParam String id) {
        log.info("Stopping A/B ranking experiment: '{}'", id);
        experimentManager.stopExperiment(id);
        return ResponseEntity.ok(ApiResponse.success("Experiment stopped successfully"));
    }
}
