package com.atlas.keywordsearch.copilot;

import com.atlas.domain.exception.AtlasException;
import com.atlas.keywordsearch.config.GeminiProperties;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

@Slf4j
@Component
@RequiredArgsConstructor
public class GeminiRequestBuilder {

    private final GeminiProperties geminiProperties;

    public Map<String, Object> buildRequestBody(String prompt) {
        if (prompt == null || prompt.isBlank()) {
            throw new AtlasException("INVALID_GEMINI_REQUEST", "Prompt text cannot be null or empty");
        }

        // Sanitize prompt
        String sanitizedPrompt = prompt.trim();

        // Validate model name
        String model = geminiProperties.getModel();
        if (model == null || model.isBlank()) {
            throw new AtlasException("INVALID_GEMINI_CONFIG", "Gemini model name is not configured");
        }

        // Validate generationConfig parameters to prevent HTTP 400 Bad Request
        double temperature = Math.max(0.0, Math.min(2.0, geminiProperties.getTemperature()));
        double topP = Math.max(0.0, Math.min(1.0, geminiProperties.getTopP()));
        int topK = Math.max(1, geminiProperties.getTopK());
        int maxOutputTokens = Math.max(1, geminiProperties.getMaxOutputTokens());

        Map<String, Object> generationConfig = Map.of(
                "temperature", temperature,
                "topP", topP,
                "topK", topK,
                "maxOutputTokens", maxOutputTokens
        );

        Map<String, Object> userPart = Map.of("text", sanitizedPrompt);
        Map<String, Object> content = Map.of(
                "role", "user",
                "parts", List.of(userPart)
        );

        Map<String, Object> payload = Map.of(
                "contents", List.of(content),
                "generationConfig", generationConfig
        );

        log.info("[GeminiRequestBuilder] Pre-flight validation passed for model '{}', prompt length: {} chars",
                model, sanitizedPrompt.length());
        return payload;
    }
}
