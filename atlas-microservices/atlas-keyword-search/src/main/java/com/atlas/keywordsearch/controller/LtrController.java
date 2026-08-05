package com.atlas.keywordsearch.controller;

import com.atlas.common.dto.ApiResponse;
import com.atlas.domain.ltr.LtrFeatureVector;
import com.atlas.domain.ltr.LtrModel;
import com.atlas.keywordsearch.ltr.LtrFeatureExtractor;
import com.atlas.keywordsearch.ltr.LtrModelRegistry;
import com.atlas.keywordsearch.ltr.LtrRankingService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/api/v13/ltr")
@RequiredArgsConstructor
public class LtrController {

    private final LtrFeatureExtractor featureExtractor;
    private final LtrModelRegistry modelRegistry;
    private final LtrRankingService ltrRankingService;

    @GetMapping("/features")
    public ResponseEntity<ApiResponse<LtrFeatureVector>> extractFeatures(
            @RequestParam String query,
            @RequestParam String docId,
            @RequestParam(defaultValue = "0.85") double bm25,
            @RequestParam(defaultValue = "0.92") double semantic,
            @RequestParam(defaultValue = "0.35") double pageRank,
            @RequestParam(defaultValue = "0.90") double freshness) {
        log.info("Extracting LTR feature vector for query '{}' and doc '{}'", query, docId);
        LtrFeatureVector vector = featureExtractor.extractFeatures(query, docId, bm25, semantic, pageRank, freshness);
        return ResponseEntity.ok(ApiResponse.success(vector));
    }

    @GetMapping("/models")
    public ResponseEntity<ApiResponse<List<LtrModel>>> getModels() {
        log.info("Fetching registered Learning-to-Rank models");
        return ResponseEntity.ok(ApiResponse.success(modelRegistry.getAllModels()));
    }

    @PostMapping("/predict")
    public ResponseEntity<ApiResponse<Double>> predict(
            @RequestParam String query,
            @RequestParam String docId,
            @RequestParam(defaultValue = "0.85") double bm25,
            @RequestParam(defaultValue = "0.92") double semantic,
            @RequestParam(defaultValue = "0.35") double pageRank,
            @RequestParam(defaultValue = "0.90") double freshness) {
        log.info("Executing LTR ranking inference for query '{}'", query);
        double score = ltrRankingService.predictScore(query, docId, bm25, semantic, pageRank, freshness);
        return ResponseEntity.ok(ApiResponse.success(score));
    }
}
