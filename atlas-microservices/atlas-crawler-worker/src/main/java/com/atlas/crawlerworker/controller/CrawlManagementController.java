package com.atlas.crawlerworker.controller;

import com.atlas.common.dto.*;
import com.atlas.crawlerworker.service.CrawlSchedulerService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/api/v1/crawl")
@RequiredArgsConstructor
public class CrawlManagementController {

    private final CrawlSchedulerService schedulerService;

    @PostMapping("/jobs")
    public ResponseEntity<ApiResponse<CrawlJobResponse>> submitCrawlJob(@Valid @RequestBody CrawlJobRequest request) {
        log.info("Received crawl job request for jobName: '{}', seeds: {}", request.getJobName(), request.getSeedUrls());
        CrawlJobResponse response = schedulerService.createJob(request);
        return ResponseEntity.ok(ApiResponse.success("Crawl job created and scheduled", response));
    }

    @GetMapping("/jobs")
    public ResponseEntity<ApiResponse<List<CrawlJobResponse>>> getAllCrawlJobs() {
        log.info("Fetching all registered crawl jobs");
        List<CrawlJobResponse> jobs = schedulerService.getAllJobs();
        return ResponseEntity.ok(ApiResponse.success(jobs));
    }

    @GetMapping("/jobs/{jobId}")
    public ResponseEntity<ApiResponse<CrawlJobResponse>> getCrawlJob(@PathVariable String jobId) {
        log.info("Fetching crawl job details for id: '{}'", jobId);
        CrawlJobResponse job = schedulerService.getJob(jobId);
        return ResponseEntity.ok(ApiResponse.success(job));
    }

    @PostMapping("/jobs/{jobId}/pause")
    public ResponseEntity<ApiResponse<CrawlJobResponse>> pauseCrawlJob(@PathVariable String jobId) {
        log.info("Pausing crawl job id: '{}'", jobId);
        CrawlJobResponse response = schedulerService.pauseJob(jobId);
        return ResponseEntity.ok(ApiResponse.success("Crawl job paused", response));
    }

    @PostMapping("/jobs/{jobId}/resume")
    public ResponseEntity<ApiResponse<CrawlJobResponse>> resumeCrawlJob(@PathVariable String jobId) {
        log.info("Resuming crawl job id: '{}'", jobId);
        CrawlJobResponse response = schedulerService.resumeJob(jobId);
        return ResponseEntity.ok(ApiResponse.success("Crawl job resumed", response));
    }

    @PostMapping("/jobs/{jobId}/cancel")
    public ResponseEntity<ApiResponse<CrawlJobResponse>> cancelCrawlJob(@PathVariable String jobId) {
        log.info("Cancelling crawl job id: '{}'", jobId);
        CrawlJobResponse response = schedulerService.cancelJob(jobId);
        return ResponseEntity.ok(ApiResponse.success("Crawl job cancelled", response));
    }

    @GetMapping("/jobs/{jobId}/statistics")
    public ResponseEntity<ApiResponse<CrawlJobStatisticsDto>> getCrawlJobStatistics(@PathVariable String jobId) {
        log.info("Fetching statistics for crawl job id: '{}'", jobId);
        CrawlJobStatisticsDto stats = schedulerService.getJobStatistics(jobId);
        return ResponseEntity.ok(ApiResponse.success(stats));
    }

    @GetMapping("/jobs/{jobId}/urls")
    public ResponseEntity<ApiResponse<PageResponse<CrawlUrlDto>>> getCrawlJobUrls(
            @PathVariable String jobId,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        log.info("Fetching paged URLs for crawl job id: '{}' (page: {}, size: {})", jobId, page, size);
        PageResponse<CrawlUrlDto> urls = schedulerService.getJobUrls(jobId, page, size);
        return ResponseEntity.ok(ApiResponse.success(urls));
    }
}
