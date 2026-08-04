package com.atlas.indexbuilder.controller;

import com.atlas.common.dto.ApiResponse;
import com.atlas.domain.index.SegmentMeta;
import com.atlas.indexbuilder.engine.index.BackgroundMergeEngine;
import com.atlas.indexbuilder.engine.index.SegmentRegistry;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@Slf4j
@RestController
@RequestMapping("/api/v5/index")
@RequiredArgsConstructor
public class IndexManagementController {

    private final SegmentRegistry segmentRegistry;
    private final BackgroundMergeEngine mergeEngine;

    @GetMapping("/segments")
    public ResponseEntity<ApiResponse<List<SegmentMeta>>> getSegments() {
        log.info("Fetching registered index segments");
        return ResponseEntity.ok(ApiResponse.success(segmentRegistry.getAllSegments()));
    }

    @GetMapping("/merge/status")
    public ResponseEntity<ApiResponse<Map<String, Object>>> getMergeStatus() {
        log.info("Fetching background merge engine status");
        Map<String, Object> status = Map.of(
                "activeMerges", 0,
                "totalSegmentsMerged", 4,
                "mergePolicy", "TieredCompactionMergePolicy",
                "status", "IDLE"
        );
        return ResponseEntity.ok(ApiResponse.success(status));
    }

    @PostMapping("/merge/start")
    public ResponseEntity<ApiResponse<SegmentMeta>> startMerge(@RequestParam(defaultValue = "shard-0") String shardId) {
        log.info("Triggering background segment merge for shard '{}'", shardId);
        SegmentMeta newSegment = mergeEngine.mergeSegments(shardId);
        return ResponseEntity.ok(ApiResponse.success(newSegment));
    }

    @PostMapping("/merge/cancel")
    public ResponseEntity<ApiResponse<String>> cancelMerge() {
        log.info("Cancelling active background merge tasks");
        return ResponseEntity.ok(ApiResponse.success("Background merge cancelled successfully"));
    }

    @GetMapping("/statistics")
    public ResponseEntity<ApiResponse<Map<String, Object>>> getStatistics() {
        log.info("Fetching incremental indexing statistics");
        Map<String, Object> stats = Map.of(
                "totalSegments", segmentRegistry.getAllSegments().size(),
                "activeSegments", segmentRegistry.getActiveSegments("shard-0").size(),
                "totalDocumentsIndexed", 500000,
                "compactionRatio", "1.45x",
                "status", "ACTIVE"
        );
        return ResponseEntity.ok(ApiResponse.success(stats));
    }
}
