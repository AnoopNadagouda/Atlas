package com.atlas.keywordsearch.controller;

import com.atlas.common.dto.ApiResponse;
import com.atlas.keywordsearch.benchmark.PerformanceBenchmarkSuite;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@Slf4j
@RestController
@RequestMapping("/api/v12/benchmark")
@RequiredArgsConstructor
public class BenchmarkController {

    private final PerformanceBenchmarkSuite benchmarkSuite;

    @GetMapping("/run")
    public ResponseEntity<ApiResponse<Map<String, Object>>> runBenchmark() {
        log.info("Running Atlas Production Performance Benchmark Suite...");
        Map<String, Object> results = benchmarkSuite.runBenchmarks();
        return ResponseEntity.ok(ApiResponse.success(results));
    }

    @GetMapping("/report")
    public ResponseEntity<ApiResponse<Map<String, Object>>> getBenchmarkReport() {
        log.info("Fetching latest benchmark execution report");
        return ResponseEntity.ok(ApiResponse.success(benchmarkSuite.runBenchmarks()));
    }
}
