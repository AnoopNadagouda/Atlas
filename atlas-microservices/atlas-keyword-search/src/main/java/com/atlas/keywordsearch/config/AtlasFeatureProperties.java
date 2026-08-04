package com.atlas.keywordsearch.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Data
@Configuration
@ConfigurationProperties(prefix = "atlas.features")
public class AtlasFeatureProperties {
    private boolean keywordSearch = true;
    private boolean semanticSearch = true;
    private boolean hybridSearch = true;
    private boolean vectorSearch = true;
    private boolean aiCopilot = false;
    private boolean knowledgeGraph = false;
}
