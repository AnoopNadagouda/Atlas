package com.atlas.crawlerworker.service;

import com.atlas.domain.model.enums.CrawlStatus;
import com.atlas.kafka.events.RawDocumentEvent;
import com.atlas.crawlerworker.entity.CrawlJobEntity;
import com.atlas.crawlerworker.entity.CrawlUrlEntity;
import com.atlas.crawlerworker.frontier.UrlFrontier;
import com.atlas.crawlerworker.frontier.UrlNormalizer;
import com.atlas.crawlerworker.http.ResilientFetcher;
import com.atlas.crawlerworker.pipeline.RawDocumentProducer;
import com.atlas.crawlerworker.repository.CrawlJobRepository;
import com.atlas.crawlerworker.repository.CrawlUrlRepository;
import com.atlas.crawlerworker.robots.RobotsManager;
import com.atlas.crawlerworker.sitemap.SitemapParser;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.time.Instant;

@Slf4j
@Service
@RequiredArgsConstructor
public class DistributedCrawlerService {

    private final CrawlJobRepository jobRepository;
    private final CrawlUrlRepository urlRepository;
    private final UrlFrontier frontier;
    private final RobotsManager robotsManager;
    private final SitemapParser sitemapParser;
    private final ResilientFetcher fetcher;
    private final RawDocumentProducer documentProducer;

    @Async
    public void startWorkerLoop(String jobId) {
        log.info("Starting crawler worker processing loop for jobId: {}", jobId);

        while (true) {
            CrawlJobEntity job = jobRepository.findById(jobId).orElse(null);
            if (job == null || job.getStatus() != CrawlStatus.RUNNING) {
                log.info("Stopping worker loop for jobId: {} (Status: {})", jobId, job != null ? job.getStatus() : "NULL");
                break;
            }

            if (job.getPagesCrawled() >= job.getMaxPages()) {
                log.info("Reached maximum page limit ({}) for jobId: {}", job.getMaxPages(), jobId);
                job.setStatus(CrawlStatus.COMPLETED);
                job.setUpdatedAt(Instant.now());
                jobRepository.save(job);
                break;
            }

            UrlFrontier.CrawlTask task = frontier.poll();
            if (task == null) {
                try {
                    Thread.sleep(500);
                } catch (InterruptedException ie) {
                    Thread.currentThread().interrupt();
                    break;
                }
                if (frontier.queueSize() == 0) {
                    log.info("Frontier queue empty. Completing crawl jobId: {}", jobId);
                    job.setStatus(CrawlStatus.COMPLETED);
                    job.setUpdatedAt(Instant.now());
                    jobRepository.save(job);
                    break;
                }
                continue;
            }

            processTask(job, task);
        }
    }

    private void processTask(CrawlJobEntity job, UrlFrontier.CrawlTask task) {
        String url = task.getUrl();
        String normalizedUrl = task.getNormalizedUrl();

        // 1. Check Robots.txt
        if (!robotsManager.isAllowed(normalizedUrl)) {
            log.warn("URL disallowed by robots.txt: {}", normalizedUrl);
            saveUrlRecord(job.getId(), url, normalizedUrl, task.getParentUrl(), task.getDepth(), "BLOCKED_ROBOTS", 403, null, "Disallowed by robots.txt");
            return;
        }

        // 2. Execute HTTP Fetch
        ResilientFetcher.FetchResult result = fetcher.fetch(normalizedUrl);

        if (result.isSuccess()) {
            job.setPagesCrawled(job.getPagesCrawled() + 1);
            job.setUpdatedAt(Instant.now());
            jobRepository.save(job);

            saveUrlRecord(job.getId(), url, normalizedUrl, task.getParentUrl(), task.getDepth(), "FETCHED", result.getStatusCode(), result.getContentType(), null);

            // 3. Publish Raw HTML Event to Kafka
            RawDocumentEvent event = RawDocumentEvent.builder()
                    .documentId("doc-" + System.currentTimeMillis())
                    .jobId(job.getId())
                    .url(url)
                    .normalizedUrl(normalizedUrl)
                    .parentUrl(task.getParentUrl())
                    .depth(task.getDepth())
                    .httpStatus(result.getStatusCode())
                    .contentType(result.getContentType())
                    .responseHeaders(result.getHeaders())
                    .rawHtml(result.getRawHtml())
                    .fetchTimestamp(Instant.now())
                    .build();

            documentProducer.publishRawDocument(event);

            // 4. Discover Sitemap if root domain
            if (task.getDepth() == 0 && (normalizedUrl.endsWith("/sitemap.xml") || result.getContentType().contains("xml"))) {
                var discovered = sitemapParser.extractUrls(result.getRawHtml());
                for (String sitemapUrl : discovered) {
                    String norm = UrlNormalizer.normalize(sitemapUrl);
                    if (!norm.isEmpty() && task.getDepth() + 1 <= job.getMaxDepth()) {
                        frontier.schedule(UrlFrontier.CrawlTask.builder()
                                .jobId(job.getId())
                                .url(sitemapUrl)
                                .normalizedUrl(norm)
                                .parentUrl(normalizedUrl)
                                .depth(task.getDepth() + 1)
                                .priority(5)
                                .build());
                    }
                }
            }
        } else {
            job.setPagesFailed(job.getPagesFailed() + 1);
            job.setUpdatedAt(Instant.now());
            jobRepository.save(job);

            saveUrlRecord(job.getId(), url, normalizedUrl, task.getParentUrl(), task.getDepth(), "FAILED", result.getStatusCode(), null, result.getErrorMessage());
        }
    }

    private void saveUrlRecord(String jobId, String url, String normUrl, String parentUrl, int depth, String status, int httpStatus, String contentType, String error) {
        CrawlUrlEntity entity = CrawlUrlEntity.builder()
                .jobId(jobId)
                .url(url)
                .normalizedUrl(normUrl)
                .parentUrl(parentUrl)
                .depth(depth)
                .status(status)
                .httpStatus(httpStatus)
                .contentType(contentType)
                .errorMessage(error)
                .fetchedAt(Instant.now())
                .build();

        urlRepository.save(entity);
    }
}
