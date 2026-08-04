package com.atlas.keywordsearch.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Data
@Configuration
@ConfigurationProperties(prefix = "atlas.cluster")
public class AtlasClusterProperties {
    private boolean enabled = true;
    private boolean discovery = true;
    private boolean distributedSearch = true;
    private String nodeId = "search-node-1";
    private String host = "localhost";
    private int port = 8082;
    private long heartbeatIntervalMs = 5000;
}
