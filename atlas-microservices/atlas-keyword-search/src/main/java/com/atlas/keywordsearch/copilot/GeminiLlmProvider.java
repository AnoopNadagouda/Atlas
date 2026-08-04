package com.atlas.keywordsearch.copilot;

import com.atlas.common.utils.JsonUtils;
import com.atlas.domain.copilot.LlmProvider;
import com.atlas.domain.exception.AtlasException;
import com.atlas.keywordsearch.config.GeminiProperties;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.Duration;
import java.util.Map;
import java.util.function.Consumer;

@Slf4j
@Component("geminiLlmProvider")
public class GeminiLlmProvider implements LlmProvider {

    private final GeminiProperties geminiProperties;
    private final GeminiRequestBuilder requestBuilder;
    private final LocalStubLlmProvider fallbackProvider;
    private final HttpClient httpClient = HttpClient.newBuilder()
            .connectTimeout(Duration.ofSeconds(5))
            .build();

    public GeminiLlmProvider(GeminiProperties geminiProperties, GeminiRequestBuilder requestBuilder, LocalStubLlmProvider fallbackProvider) {
        this.geminiProperties = geminiProperties;
        this.requestBuilder = requestBuilder;
        this.fallbackProvider = fallbackProvider;
    }

    @Override
    public String getProviderName() {
        return "Google Gemini (" + geminiProperties.getModel() + ")";
    }

    @Override
    public String generateAnswer(String prompt) {
        if (geminiProperties.getApiKey() == null || geminiProperties.getApiKey().isBlank()) {
            log.warn("[GeminiLlmProvider] Gemini API Key is missing/blank. Falling back to local RAG provider.");
            return fallbackProvider.generateAnswer(prompt);
        }

        Map<String, Object> requestPayload = requestBuilder.buildRequestBody(prompt);
        String requestJson = JsonUtils.toJson(requestPayload);

        String url = String.format("%s/%s/models/%s:generateContent?key=%s",
                geminiProperties.getEndpoint(),
                geminiProperties.getApiVersion(),
                geminiProperties.getModel(),
                geminiProperties.getApiKey());

        log.info("[GeminiLlmProvider] Executing POST request to model '{}' (Prompt length: {} chars)",
                geminiProperties.getModel(), prompt.length());

        try {
            HttpRequest httpRequest = HttpRequest.newBuilder()
                    .uri(URI.create(url))
                    .header("Content-Type", "application/json")
                    .timeout(Duration.ofMillis(geminiProperties.getTimeoutMs()))
                    .POST(HttpRequest.BodyPublishers.ofString(requestJson))
                    .build();

            HttpResponse<String> response = httpClient.send(httpRequest, HttpResponse.BodyHandlers.ofString());

            if (response.statusCode() == 400) {
                log.error("[GeminiLlmProvider] HTTP 400 Bad Request / INVALID_ARGUMENT from Gemini API! Response: {}", response.body());
                throw new AtlasException("GEMINI_INVALID_ARGUMENT", "HTTP 400 Invalid Argument sent to Gemini API: " + response.body());
            }

            if (response.statusCode() != 200) {
                log.error("[GeminiLlmProvider] Non-200 Response from Gemini API (HTTP {}): {}", response.statusCode(), response.body());
                return fallbackProvider.generateAnswer(prompt);
            }

            log.info("[GeminiLlmProvider] Successfully received HTTP 200 response from Gemini API");
            return parseResponseText(response.body());

        } catch (AtlasException e) {
            throw e;
        } catch (Exception e) {
            log.error("[GeminiLlmProvider] Failed communicating with Gemini API: {}", e.getMessage(), e);
            return fallbackProvider.generateAnswer(prompt);
        }
    }

    @Override
    public void streamAnswer(String prompt, Consumer<String> tokenConsumer) {
        log.info("[GeminiLlmProvider] Stream requested for model '{}'", geminiProperties.getModel());
        String fullAnswer = generateAnswer(prompt);
        for (String word : fullAnswer.split(" ")) {
            tokenConsumer.accept(word + " ");
            try {
                Thread.sleep(25);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                break;
            }
        }
    }

    @Override
    public boolean isHealthy() {
        return geminiProperties.getModel() != null && !geminiProperties.getModel().isBlank();
    }

    @SuppressWarnings("unchecked")
    private String parseResponseText(String responseJson) {
        try {
            Map<String, Object> map = JsonUtils.fromJson(responseJson, Map.class);
            var candidates = (java.util.List<Map<String, Object>>) map.get("candidates");
            if (candidates != null && !candidates.isEmpty()) {
                var content = (Map<String, Object>) candidates.get(0).get("content");
                if (content != null) {
                    var parts = (java.util.List<Map<String, Object>>) content.get("parts");
                    if (parts != null && !parts.isEmpty()) {
                        return (String) parts.get(0).get("text");
                    }
                }
            }
        } catch (Exception e) {
            log.error("[GeminiLlmProvider] Error parsing Gemini JSON response: {}", e.getMessage());
        }
        return "I couldn't find enough indexed information to answer confidently.";
    }
}
