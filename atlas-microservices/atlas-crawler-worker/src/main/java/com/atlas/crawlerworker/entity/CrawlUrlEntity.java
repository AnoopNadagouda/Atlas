package com.atlas.crawlerworker.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;

@Data
@Entity
@Table(name = "crawl_urls", indexes = {
    @Index(name = "idx_crawl_url_job", columnList = "job_id"),
    @Index(name = "idx_crawl_url_norm", columnList = "normalized_url")
})
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CrawlUrlEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;

    @Column(name = "job_id", nullable = false)
    private String jobId;

    @Column(nullable = false, length = 2048)
    private String url;

    @Column(name = "normalized_url", nullable = false, length = 2048)
    private String normalizedUrl;

    @Column(name = "parent_url", length = 2048)
    private String parentUrl;

    @Column(nullable = false)
    private int depth;

    @Column(nullable = false, length = 30)
    private String status; // QUEUED, FETCHED, FAILED, BLOCKED_ROBOTS

    @Column(name = "http_status")
    private int httpStatus;

    @Column(name = "content_type")
    private String contentType;

    @Column(name = "error_message", length = 1024)
    private String errorMessage;

    @Column(name = "fetched_at")
    private Instant fetchedAt;
}
