package com.atlas.keywordsearch.controller;

import com.atlas.common.dto.ApiResponse;
import com.atlas.common.dto.SearchRequest;
import com.atlas.domain.copilot.LlmProvider;
import com.atlas.keywordsearch.config.AtlasCopilotProperties;
import com.atlas.keywordsearch.config.AtlasFeatureProperties;
import com.atlas.keywordsearch.copilot.AiCopilotService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import java.util.List;
import java.util.Map;

@Slf4j
@RestController
@RequestMapping("/api/v3/copilot")
@RequiredArgsConstructor
public class CopilotController {

    private final AiCopilotService aiCopilotService;
    private final LlmProvider llmProvider;
    private final AtlasCopilotProperties copilotProperties;
    private final AtlasFeatureProperties featureProperties;

    @PostMapping("/chat")
    public ResponseEntity<ApiResponse<Map<String, Object>>> chat(@Valid @RequestBody SearchRequest request) {
        log.info("API v3 Copilot Grounded Chat request for question: '{}'", request.getQuery());
        Map<String, Object> result = aiCopilotService.generateGroundedChat(request);
        return ResponseEntity.ok(ApiResponse.success(result));
    }

    @PostMapping(value = "/stream", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    public SseEmitter stream(@Valid @RequestBody SearchRequest request) {
        log.info("API v3 Copilot SSE Stream request for question: '{}'", request.getQuery());
        return aiCopilotService.streamGroundedChat(request);
    }

    @GetMapping("/providers")
    public ResponseEntity<ApiResponse<List<Map<String, Object>>>> getProviders() {
        log.info("Fetching available LLM providers");
        return ResponseEntity.ok(ApiResponse.success(List.of(
                Map.of("name", "Local-RAG-Engine", "status", "ACTIVE", "type", "Local Stub / Llama-3"),
                Map.of("name", "OpenAI GPT-4o", "status", "CONFIGURED", "type", "OpenAI REST API"),
                Map.of("name", "Google Gemini 1.5 Pro", "status", "CONFIGURED", "type", "Google Cloud API")
        )));
    }

    @GetMapping("/config")
    public ResponseEntity<ApiResponse<Map<String, Object>>> getConfig() {
        log.info("Fetching AI Copilot configuration");
        return ResponseEntity.ok(ApiResponse.success(Map.of(
                "provider", copilotProperties.getProvider(),
                "maxContextTokens", copilotProperties.getMaxContextTokens(),
                "maxOutputTokens", copilotProperties.getMaxOutputTokens(),
                "temperature", copilotProperties.getTemperature(),
                "copilotEnabled", featureProperties.isAiCopilot(),
                "streamingEnabled", featureProperties.isStreaming(),
                "citationsEnabled", featureProperties.isCitations()
        )));
    }
}
