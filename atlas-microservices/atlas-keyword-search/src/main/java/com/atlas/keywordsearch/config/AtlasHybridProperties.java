package com.atlas.keywordsearch.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Data
@Configuration
@ConfigurationProperties(prefix = "atlas.hybrid")
public class AtlasHybridProperties {
    private int rrfK = 60;
    private long timeoutMs = 3000;
}
