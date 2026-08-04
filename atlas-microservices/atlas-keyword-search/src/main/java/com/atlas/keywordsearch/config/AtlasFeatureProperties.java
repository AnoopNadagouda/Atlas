package com.atlas.keywordsearch.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Data
@Configuration
@ConfigurationProperties(prefix = "atlas.features")
public class AtlasFeatureProperties {
    private boolean semanticSearch = false;
    private boolean hybridSearch = false;
    private boolean vectorSearch = false;
    private boolean aiCopilot = false;
    private boolean knowledgeGraph = false;
}
