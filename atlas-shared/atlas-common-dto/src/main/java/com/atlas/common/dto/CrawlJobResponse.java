package com.atlas.common.dto;

import com.atlas.domain.model.enums.CrawlStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CrawlJobResponse {
    private String jobId;
    private String jobName;
    private CrawlStatus status;
    private int pagesCrawled;
    private Instant createdAt;
    private Instant updatedAt;
}
