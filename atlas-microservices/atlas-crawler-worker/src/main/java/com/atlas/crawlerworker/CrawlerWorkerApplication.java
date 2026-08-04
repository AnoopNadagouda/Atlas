package com.atlas.crawlerworker;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;

@EnableAsync
@SpringBootApplication
public class CrawlerWorkerApplication {

    public static void main(String[] args) {
        SpringApplication.run(CrawlerWorkerApplication.class, args);
    }
}
