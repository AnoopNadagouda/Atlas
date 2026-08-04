package com.atlas.crawlerworker.http;

import com.atlas.common.utils.RetryUtils;
import com.atlas.crawlerworker.config.CrawlerProperties;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.charset.StandardCharsets;
import java.time.Duration;
import java.util.HashMap;
import java.util.Map;
import java.util.zip.GZIPInputStream;

@Slf4j
@Component
@RequiredArgsConstructor
public class ResilientFetcher {

    private final CrawlerProperties properties;

    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class FetchResult {
        private String url;
        private int statusCode;
        private String contentType;
        private Map<String, String> headers;
        private String rawHtml;
        private boolean success;
        private String errorMessage;
    }

    public FetchResult fetch(String urlStr) {
        return RetryUtils.executeWithRetry(() -> executeFetch(urlStr), properties.getMaxRetries(), 500);
    }

    private FetchResult executeFetch(String urlStr) {
        try (HttpClient client = HttpClient.newBuilder()
                .followRedirects(HttpClient.Redirect.NORMAL)
                .connectTimeout(Duration.ofMillis(properties.getConnectionTimeoutMs()))
                .build()) {

            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create(urlStr))
                    .header("User-Agent", properties.getUserAgent())
                    .header("Accept-Encoding", "gzip, deflate")
                    .timeout(Duration.ofMillis(properties.getReadTimeoutMs()))
                    .GET()
                    .build();

            HttpResponse<byte[]> response = client.send(request, HttpResponse.BodyHandlers.ofByteArray());
            int statusCode = response.statusCode();

            Map<String, String> headers = new HashMap<>();
            response.headers().map().forEach((k, v) -> headers.put(k.toLowerCase(), String.join(", ", v)));

            String contentType = headers.getOrDefault("content-type", "text/html");
            String encoding = headers.getOrDefault("content-encoding", "");

            byte[] bodyBytes = response.body();
            if ("gzip".equalsIgnoreCase(encoding) && bodyBytes.length > 0) {
                bodyBytes = decompressGzip(bodyBytes);
            }

            String html = new String(bodyBytes, StandardCharsets.UTF_8);

            return FetchResult.builder()
                    .url(urlStr)
                    .statusCode(statusCode)
                    .contentType(contentType)
                    .headers(headers)
                    .rawHtml(html)
                    .success(statusCode >= 200 && statusCode < 300)
                    .build();

        } catch (Exception e) {
            log.warn("HTTP fetch error for URL '{}': {}", urlStr, e.getMessage());
            return FetchResult.builder()
                    .url(urlStr)
                    .statusCode(500)
                    .success(false)
                    .errorMessage(e.getMessage())
                    .build();
        }
    }

    private byte[] decompressGzip(byte[] compressed) {
        try (InputStream bio = new ByteArrayInputStream(compressed);
             GZIPInputStream gzio = new GZIPInputStream(bio);
             ByteArrayOutputStream out = new ByteArrayOutputStream()) {
            byte[] buffer = new byte[1024];
            int len;
            while ((len = gzio.read(buffer)) > 0) {
                out.write(buffer, 0, len);
            }
            return out.toByteArray();
        } catch (Exception e) {
            return compressed;
        }
    }
}
