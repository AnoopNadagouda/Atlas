package com.atlas.crawlerworker.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Data
@Configuration
@ConfigurationProperties(prefix = "atlas.crawler")
public class CrawlerProperties {

    private String userAgent = "AtlasBot/1.0 (+https://atlas.search/bot)";
    private int defaultMaxDepth = 3;
    private int defaultMaxPages = 10000;
    private int defaultMaxConcurrency = 5;
    private int connectionTimeoutMs = 10000;
    private int readTimeoutMs = 15000;
    private int maxRetries = 3;
    private int maxRedirects = 5;
    private long domainDelayMs = 500;
}
