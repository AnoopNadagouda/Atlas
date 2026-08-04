package com.atlas.common.dto;

import com.atlas.domain.model.enums.CrawlStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CrawlJobStatisticsDto {
    private String jobId;
    private String jobName;
    private CrawlStatus status;
    private int pagesCrawled;
    private int pagesFailed;
    private int urlsQueued;
    private int urlsBlockedRobots;
    private long elapsedTimeSeconds;
    private double currentPagesPerSecond;
}
