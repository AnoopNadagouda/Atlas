package com.atlas.common.dto;

import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CrawlJobRequest {

    private String jobName;

    @NotEmpty(message = "Seed URLs must not be empty")
    private List<String> seedUrls;

    @Builder.Default
    private int maxDepth = 3;

    @Builder.Default
    private int maxPages = 10000;

    private String cronSchedule;
}
