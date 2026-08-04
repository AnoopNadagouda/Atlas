package com.atlas.indexbuilder.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Data
@Configuration
@ConfigurationProperties(prefix = "atlas.index")
public class IndexProperties {
    private String storagePath = "./data/index_segments";
    private int batchSize = 100;
}
