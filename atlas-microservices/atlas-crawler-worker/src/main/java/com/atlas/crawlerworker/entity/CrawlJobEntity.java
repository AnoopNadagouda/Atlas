package com.atlas.crawlerworker.entity;

import com.atlas.domain.model.enums.CrawlStatus;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;

@Data
@Entity
@Table(name = "crawl_jobs")
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CrawlJobEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;

    @Column(name = "job_name", nullable = false)
    private String jobName;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 20)
    @Builder.Default
    private CrawlStatus status = CrawlStatus.PENDING;

    @Column(name = "max_depth")
    @Builder.Default
    private int maxDepth = 3;

    @Column(name = "max_pages")
    @Builder.Default
    private int maxPages = 10000;

    @Column(name = "max_concurrency")
    @Builder.Default
    private int maxConcurrency = 5;

    @Column(name = "pages_crawled")
    @Builder.Default
    private int pagesCrawled = 0;

    @Column(name = "pages_failed")
    @Builder.Default
    private int pagesFailed = 0;

    @Column(name = "cron_schedule")
    private String cronSchedule;

    @Column(name = "user_agent")
    private String userAgent;

    @Column(name = "created_at", nullable = false)
    @Builder.Default
    private Instant createdAt = Instant.now();

    @Column(name = "updated_at", nullable = false)
    @Builder.Default
    private Instant updatedAt = Instant.now();
}
