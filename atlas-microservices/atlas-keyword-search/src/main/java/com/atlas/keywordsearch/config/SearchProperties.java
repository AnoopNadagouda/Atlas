package com.atlas.keywordsearch.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Data
@Configuration
@ConfigurationProperties(prefix = "atlas.search")
public class SearchProperties {
    private double k1 = 1.2;
    private double b = 0.75;
    private double titleBoost = 2.0;
    private double headingBoost = 1.5;
    private double bodyBoost = 1.0;
    private String storagePath = "./data/index_segments";
    private long cacheTtlSeconds = 600;
}
