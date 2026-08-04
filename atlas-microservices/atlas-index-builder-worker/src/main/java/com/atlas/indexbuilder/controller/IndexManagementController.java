package com.atlas.indexbuilder.controller;

import com.atlas.common.dto.*;
import com.atlas.indexbuilder.pipeline.IndexCoordinatorService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.Map;

@Slf4j
@RestController
@RequestMapping("/api/v1/index")
@RequiredArgsConstructor
public class IndexManagementController {

    private final IndexCoordinatorService coordinatorService;

    @PostMapping("/build")
    public ResponseEntity<ApiResponse<IndexSegmentDto>> buildSegment() throws IOException {
        log.info("Triggering manual index segment flush");
        IndexSegmentDto segment = coordinatorService.flushSegment();
        return ResponseEntity.ok(ApiResponse.success("Inverted index segment generated successfully", segment));
    }

    @GetMapping("/statistics")
    public ResponseEntity<ApiResponse<IndexStatisticsDto>> getStatistics() {
        log.info("Fetching global index collection statistics");
        IndexStatisticsDto stats = coordinatorService.getStatistics();
        return ResponseEntity.ok(ApiResponse.success(stats));
    }

    @GetMapping("/segments")
    public ResponseEntity<ApiResponse<PageResponse<IndexSegmentDto>>> getSegments(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        log.info("Fetching paged index segments (page: {}, size: {})", page, size);
        PageResponse<IndexSegmentDto> segments = coordinatorService.getSegments(page, size);
        return ResponseEntity.ok(ApiResponse.success(segments));
    }

    @GetMapping("/segments/{id}")
    public ResponseEntity<ApiResponse<IndexSegmentDto>> getSegment(@PathVariable String id) {
        log.info("Fetching index segment details for id: '{}'", id);
        IndexSegmentDto segment = coordinatorService.getSegment(id);
        return ResponseEntity.ok(ApiResponse.success(segment));
    }

    @GetMapping("/status")
    public ResponseEntity<ApiResponse<Map<String, String>>> getStatus() {
        log.info("Index Builder status check requested");
        return ResponseEntity.ok(ApiResponse.success(Map.of(
                "status", "UP",
                "engine", "Custom Inverted Index Engine v1.0",
                "service", "atlas-index-builder-worker"
        )));
    }
}
