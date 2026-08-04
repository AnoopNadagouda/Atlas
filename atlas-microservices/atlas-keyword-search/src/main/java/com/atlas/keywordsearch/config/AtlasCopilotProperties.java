package com.atlas.keywordsearch.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Data
@Configuration
@ConfigurationProperties(prefix = "atlas.copilot")
public class AtlasCopilotProperties {
    private String provider = "local";
    private int maxContextTokens = 2048;
    private int maxOutputTokens = 512;
    private double temperature = 0.2;
}
