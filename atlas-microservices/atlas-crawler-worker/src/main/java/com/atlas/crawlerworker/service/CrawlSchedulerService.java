package com.atlas.crawlerworker.service;

import com.atlas.common.dto.*;
import com.atlas.domain.exception.ResourceNotFoundException;
import com.atlas.domain.exception.ValidationException;
import com.atlas.domain.model.enums.CrawlStatus;
import com.atlas.crawlerworker.entity.CrawlJobEntity;
import com.atlas.crawlerworker.entity.CrawlUrlEntity;
import com.atlas.crawlerworker.frontier.UrlFrontier;
import com.atlas.crawlerworker.frontier.UrlNormalizer;
import com.atlas.crawlerworker.repository.CrawlJobRepository;
import com.atlas.crawlerworker.repository.CrawlUrlRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Duration;
import java.time.Instant;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class CrawlSchedulerService {

    private final CrawlJobRepository jobRepository;
    private final CrawlUrlRepository urlRepository;
    private final UrlFrontier frontier;
    private final DistributedCrawlerService crawlerService;

    @Transactional
    public CrawlJobResponse createJob(CrawlJobRequest request) {
        if (request.getSeedUrls() == null || request.getSeedUrls().isEmpty()) {
            throw new ValidationException("Seed URLs must not be empty");
        }

        CrawlJobEntity entity = CrawlJobEntity.builder()
                .jobName(request.getJobName() != null ? request.getJobName() : "Crawl Job " + System.currentTimeMillis())
                .status(CrawlStatus.RUNNING)
                .maxDepth(request.getMaxDepth())
                .maxPages(request.getMaxPages())
                .maxConcurrency(request.getMaxConcurrency())
                .cronSchedule(request.getCronSchedule())
                .userAgent(request.getUserAgent())
                .createdAt(Instant.now())
                .updatedAt(Instant.now())
                .build();

        CrawlJobEntity saved = jobRepository.save(entity);

        // Schedule seeds into frontier
        for (String rawSeed : request.getSeedUrls()) {
            String normSeed = UrlNormalizer.normalize(rawSeed);
            if (!normSeed.isEmpty()) {
                frontier.schedule(UrlFrontier.CrawlTask.builder()
                        .jobId(saved.getId())
                        .url(rawSeed)
                        .normalizedUrl(normSeed)
                        .parentUrl(null)
                        .depth(0)
                        .priority(10)
                        .build());
            }
        }

        // Trigger worker processing asynchronously
        crawlerService.startWorkerLoop(saved.getId());

        return mapToResponse(saved, request.getSeedUrls());
    }

    public List<CrawlJobResponse> getAllJobs() {
        return jobRepository.findAll().stream()
                .map(job -> mapToResponse(job, List.of()))
                .toList();
    }

    public CrawlJobResponse getJob(String jobId) {
        CrawlJobEntity job = jobRepository.findById(jobId)
                .orElseThrow(() -> new ResourceNotFoundException("CrawlJob", jobId));
        return mapToResponse(job, List.of());
    }

    @Transactional
    public CrawlJobResponse pauseJob(String jobId) {
        CrawlJobEntity job = jobRepository.findById(jobId)
                .orElseThrow(() -> new ResourceNotFoundException("CrawlJob", jobId));
        job.setStatus(CrawlStatus.PAUSED);
        job.setUpdatedAt(Instant.now());
        return mapToResponse(jobRepository.save(job), List.of());
    }

    @Transactional
    public CrawlJobResponse resumeJob(String jobId) {
        CrawlJobEntity job = jobRepository.findById(jobId)
                .orElseThrow(() -> new ResourceNotFoundException("CrawlJob", jobId));
        job.setStatus(CrawlStatus.RUNNING);
        job.setUpdatedAt(Instant.now());
        crawlerService.startWorkerLoop(job.getId());
        return mapToResponse(jobRepository.save(job), List.of());
    }

    @Transactional
    public CrawlJobResponse cancelJob(String jobId) {
        CrawlJobEntity job = jobRepository.findById(jobId)
                .orElseThrow(() -> new ResourceNotFoundException("CrawlJob", jobId));
        job.setStatus(CrawlStatus.CANCELLED);
        job.setUpdatedAt(Instant.now());
        frontier.clearJob(jobId);
        return mapToResponse(jobRepository.save(job), List.of());
    }

    public CrawlJobStatisticsDto getJobStatistics(String jobId) {
        CrawlJobEntity job = jobRepository.findById(jobId)
                .orElseThrow(() -> new ResourceNotFoundException("CrawlJob", jobId));

        long elapsedSeconds = Math.max(1, Duration.between(job.getCreatedAt(), Instant.now()).getSeconds());
        double pagesPerSec = (double) job.getPagesCrawled() / elapsedSeconds;
        int queuedCount = frontier.queueSize();
        long blockedCount = urlRepository.countByJobIdAndStatus(jobId, "BLOCKED_ROBOTS");

        return CrawlJobStatisticsDto.builder()
                .jobId(job.getId())
                .jobName(job.getJobName())
                .status(job.getStatus())
                .pagesCrawled(job.getPagesCrawled())
                .pagesFailed(job.getPagesFailed())
                .urlsQueued(queuedCount)
                .urlsBlockedRobots((int) blockedCount)
                .elapsedTimeSeconds(elapsedSeconds)
                .currentPagesPerSecond(Math.round(pagesPerSec * 100.0) / 100.0)
                .build();
    }

    public PageResponse<CrawlUrlDto> getJobUrls(String jobId, int page, int size) {
        Page<CrawlUrlEntity> pageResult = urlRepository.findByJobId(jobId, PageRequest.of(page, size));
        List<CrawlUrlDto> content = pageResult.getContent().stream()
                .map(this::mapToUrlDto)
                .toList();

        return PageResponse.<CrawlUrlDto>builder()
                .content(content)
                .pageNumber(pageResult.getNumber())
                .pageSize(pageResult.getSize())
                .totalElements(pageResult.getTotalElements())
                .totalPages(pageResult.getTotalPages())
                .last(pageResult.isLast())
                .build();
    }

    private CrawlJobResponse mapToResponse(CrawlJobEntity entity, List<String> seedUrls) {
        return CrawlJobResponse.builder()
                .jobId(entity.getId())
                .jobName(entity.getJobName())
                .status(entity.getStatus())
                .maxDepth(entity.getMaxDepth())
                .maxPages(entity.getMaxPages())
                .pagesCrawled(entity.getPagesCrawled())
                .pagesFailed(entity.getPagesFailed())
                .seedUrls(seedUrls)
                .createdAt(entity.getCreatedAt())
                .updatedAt(entity.getUpdatedAt())
                .build();
    }

    private CrawlUrlDto mapToUrlDto(CrawlUrlEntity entity) {
        return CrawlUrlDto.builder()
                .id(entity.getId())
                .jobId(entity.getJobId())
                .url(entity.getUrl())
                .normalizedUrl(entity.getNormalizedUrl())
                .parentUrl(entity.getParentUrl())
                .depth(entity.getDepth())
                .status(entity.getStatus())
                .httpStatus(entity.getHttpStatus())
                .contentType(entity.getContentType())
                .errorMessage(entity.getErrorMessage())
                .fetchedAt(entity.getFetchedAt())
                .build();
    }
}
