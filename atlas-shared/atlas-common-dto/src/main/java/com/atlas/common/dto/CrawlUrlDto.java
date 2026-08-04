package com.atlas.common.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CrawlUrlDto {
    private String id;
    private String jobId;
    private String url;
    private String normalizedUrl;
    private String parentUrl;
    private int depth;
    private String status;
    private int httpStatus;
    private String contentType;
    private String errorMessage;
    private Instant fetchedAt;
}
