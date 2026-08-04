package com.atlas.parserservice.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Data
@Configuration
@ConfigurationProperties(prefix = "atlas.parser")
public class ParserProperties {
    private int minContentLength = 20;
    private int simhashHammingThreshold = 3;
}
