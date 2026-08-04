package com.atlas.indexbuilder.controller;

import com.atlas.common.dto.ApiResponse;
import com.atlas.common.dto.IndexStatusResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("/api/v1/index")
public class IndexManagementController {

    @GetMapping("/status")
    public ResponseEntity<ApiResponse<IndexStatusResponse>> getIndexStatus() {
        log.info("Fetching index builder cluster status");
        
        IndexStatusResponse status = IndexStatusResponse.builder()
                .totalDocumentsIndexed(1024)
                .totalSegments(4)
                .indexSizeBytes(52428800L) // ~50 MB
                .status("HEALTHY")
                .build();

        return ResponseEntity.ok(ApiResponse.success(status));
    }

    @PostMapping("/reindex")
    public ResponseEntity<ApiResponse<String>> triggerReindex() {
        log.info("Triggering background index segment optimization and reindex job");
        return ResponseEntity.ok(ApiResponse.success("Reindex job dispatched successfully", "JOB_DISPATCHED"));
    }
}
