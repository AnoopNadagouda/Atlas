package com.atlas.agent.client;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;
import reactor.util.retry.Retry;

import java.time.Duration;
import java.util.Map;

@Slf4j
@Component
public class AtlasServiceClient {

    private final WebClient webClient;

    @Value("${atlas.services.keyword-search.url:http://localhost:8082}")
    private String searchServiceUrl;

    @Value("${atlas.services.crawler.url:http://localhost:8083}")
    private String crawlerServiceUrl;

    @Value("${atlas.services.parser.url:http://localhost:8085}")
    private String parserServiceUrl;

    public AtlasServiceClient(WebClient.Builder webClientBuilder) {
        this.webClient = webClientBuilder.build();
    }

    public Map<String, Object> executeSearch(String query, int page, int size) {
        String uri = searchServiceUrl + "/api/v1/search?q=" + query + "&page=" + page + "&size=" + size;
        log.info("Dispatching HTTP GET to Search Service: {}", uri);
        return webClient.get()
                .uri(uri)
                .retrieve()
                .bodyToMono(Map.class)
                .retryWhen(Retry.backoff(2, Duration.ofMillis(200)))
                .onErrorResume(e -> {
                    log.warn("Search Service call failed or unavailable ({}), returning fallback", e.getMessage());
                    return Mono.just(Map.of(
                            "query", query,
                            "results", Map.of("content", java.util.List.of()),
                            "totalHits", 0,
                            "status", "FALLBACK_OFFLINE"
                    ));
                })
                .block(Duration.ofSeconds(10));
    }

    public Map<String, Object> executeVectorSearch(String query, int topK) {
        String uri = searchServiceUrl + "/api/v1/search?q=" + query + "&mode=vector&size=" + topK;
        log.info("Dispatching HTTP GET Vector Search to Search Service: {}", uri);
        return webClient.get()
                .uri(uri)
                .retrieve()
                .bodyToMono(Map.class)
                .retryWhen(Retry.backoff(2, Duration.ofMillis(200)))
                .onErrorResume(e -> {
                    log.warn("Vector Search Service call failed ({}), returning fallback", e.getMessage());
                    return Mono.just(Map.of(
                            "query", query,
                            "mode", "vector",
                            "results", Map.of("content", java.util.List.of()),
                            "status", "FALLBACK_OFFLINE"
                    ));
                })
                .block(Duration.ofSeconds(10));
    }

    public Map<String, Object> dispatchCrawlJob(String seedUrl, int maxDepth, int maxPages) {
        String uri = crawlerServiceUrl + "/api/v1/crawl/jobs";
        log.info("Dispatching HTTP POST to Crawler Worker: {}", uri);
        Map<String, Object> requestBody = Map.of(
                "seedUrl", seedUrl,
                "maxDepth", maxDepth,
                "maxPages", maxPages
        );
        return webClient.post()
                .uri(uri)
                .bodyValue(requestBody)
                .retrieve()
                .bodyToMono(Map.class)
                .retryWhen(Retry.backoff(2, Duration.ofMillis(200)))
                .onErrorResume(e -> {
                    log.warn("Crawler Worker call failed ({}), returning fallback", e.getMessage());
                    return Mono.just(Map.of(
                            "jobId", "crawl-job-stub-" + System.currentTimeMillis(),
                            "seedUrl", seedUrl,
                            "status", "SUBMITTED_FALLBACK"
                    ));
                })
                .block(Duration.ofSeconds(10));
    }

    public Map<String, Object> parseDocument(String rawHtml, String url) {
        String uri = parserServiceUrl + "/api/v1/parser/clean";
        log.info("Dispatching HTTP POST to Document Parser: {}", uri);
        Map<String, Object> requestBody = Map.of(
                "html", rawHtml,
                "url", url != null ? url : "https://example.com"
        );
        return webClient.post()
                .uri(uri)
                .bodyValue(requestBody)
                .retrieve()
                .bodyToMono(Map.class)
                .retryWhen(Retry.backoff(2, Duration.ofMillis(200)))
                .onErrorResume(e -> {
                    log.warn("Parser Service call failed ({}), returning fallback", e.getMessage());
                    return Mono.just(Map.of(
                            "url", url != null ? url : "",
                            "cleanText", rawHtml != null ? rawHtml.replaceAll("<[^>]*>", "") : "",
                            "status", "PARSED_FALLBACK"
                    ));
                })
                .block(Duration.ofSeconds(10));
    }
}
