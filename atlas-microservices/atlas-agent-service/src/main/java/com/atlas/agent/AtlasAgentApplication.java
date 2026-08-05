package com.atlas.agent;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;

@SpringBootApplication(scanBasePackages = {"com.atlas"})
@EnableAsync
public class AtlasAgentApplication {

    public static void main(String[] args) {
        SpringApplication.run(AtlasAgentApplication.class, args);
    }
}
