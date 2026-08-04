package com.atlas.crawlerworker.robots;

import com.atlas.common.utils.RegexUtils;
import com.atlas.crawlerworker.config.CrawlerProperties;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.Duration;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Slf4j
@Component
@RequiredArgsConstructor
public class RobotsManager {

    private final CrawlerProperties properties;
    private final Map<String, RobotsTxtParser> cache = new ConcurrentHashMap<>();

    public boolean isAllowed(String urlStr) {
        String domain = RegexUtils.extractDomain(urlStr);
        if (domain.isEmpty()) return true;

        RobotsTxtParser parser = cache.computeIfAbsent(domain, this::fetchRobotsTxt);
        try {
            URI uri = new URI(urlStr);
            String path = uri.getPath();
            return parser.isAllowed("AtlasBot", path);
        } catch (Exception e) {
            return true;
        }
    }

    private RobotsTxtParser fetchRobotsTxt(String domain) {
        String robotsUrl = "https://" + domain + "/robots.txt";
        log.info("Fetching robots.txt from: {}", robotsUrl);
        try (HttpClient client = HttpClient.newBuilder().connectTimeout(Duration.ofSeconds(5)).build()) {
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create(robotsUrl))
                    .header("User-Agent", properties.getUserAgent())
                    .timeout(Duration.ofSeconds(5))
                    .GET()
                    .build();

            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
            if (response.statusCode() == 200) {
                return RobotsTxtParser.parse(response.body());
            }
        } catch (Exception e) {
            log.debug("No robots.txt found for domain: {}", domain);
        }
        return new RobotsTxtParser(); // Empty allow-all rules
    }
}
