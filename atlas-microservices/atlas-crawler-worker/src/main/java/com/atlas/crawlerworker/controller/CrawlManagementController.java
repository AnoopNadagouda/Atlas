package com.atlas.crawlerworker.controller;

import com.atlas.common.dto.ApiResponse;
import com.atlas.common.dto.CrawlJobRequest;
import com.atlas.common.dto.CrawlJobResponse;
import com.atlas.domain.model.enums.CrawlStatus;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.Instant;
import java.util.UUID;

@Slf4j
@RestController
@RequestMapping("/api/v1/crawl")
public class CrawlManagementController {

    @PostMapping("/jobs")
    public ResponseEntity<ApiResponse<CrawlJobResponse>> submitCrawlJob(@Valid @RequestBody CrawlJobRequest request) {
        log.info("Received crawl job submission for jobName: '{}', seeds: {}", request.getJobName(), request.getSeedUrls());
        
        CrawlJobResponse response = CrawlJobResponse.builder()
                .jobId("crawl-job-" + UUID.randomUUID().toString().substring(0, 8))
                .jobName(request.getJobName() != null ? request.getJobName() : "Seed Crawl Job")
                .status(CrawlStatus.PENDING)
                .pagesCrawled(0)
                .createdAt(Instant.now())
                .updatedAt(Instant.now())
                .build();

        return ResponseEntity.ok(ApiResponse.success("Crawl job registered successfully", response));
    }

    @GetMapping("/jobs/{jobId}")
    public ResponseEntity<ApiResponse<CrawlJobResponse>> getCrawlJobStatus(@PathVariable String jobId) {
        log.info("Checking status for crawl jobId: '{}'", jobId);
        
        CrawlJobResponse response = CrawlJobResponse.builder()
                .jobId(jobId)
                .jobName("Active Crawl Job")
                .status(CrawlStatus.RUNNING)
                .pagesCrawled(42)
                .createdAt(Instant.now().minusSeconds(120))
                .updatedAt(Instant.now())
                .build();

        return ResponseEntity.ok(ApiResponse.success(response));
    }
}
