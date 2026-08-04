package com.atlas.keywordsearch.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Data
@Configuration
@ConfigurationProperties(prefix = "atlas.copilot.gemini")
public class GeminiProperties {
    private String apiKey = "";
    private String model = "gemini-1.5-flash";
    private String endpoint = "https://generativelanguage.googleapis.com";
    private String apiVersion = "v1beta";
    private double temperature = 0.2;
    private double topP = 0.95;
    private int topK = 40;
    private int maxOutputTokens = 1024;
    private long timeoutMs = 10000;
}
