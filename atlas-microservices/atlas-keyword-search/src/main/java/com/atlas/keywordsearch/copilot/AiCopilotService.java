package com.atlas.keywordsearch.copilot;

import com.atlas.common.dto.PageResponse;
import com.atlas.common.dto.SearchRequest;
import com.atlas.common.dto.SearchResultDto;
import com.atlas.domain.copilot.LlmProvider;
import com.atlas.keywordsearch.hybrid.HybridSearchService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import java.io.IOException;
import java.util.*;
import java.util.concurrent.CompletableFuture;

@Slf4j
@Service
@RequiredArgsConstructor
public class AiCopilotService {

    private final HybridSearchService hybridSearchService;
    private final ContextBuilder contextBuilder;
    private final PromptBuilder promptBuilder;
    private final LlmProvider llmProvider;

    public Map<String, Object> generateGroundedChat(SearchRequest request) {
        log.info("Generating Grounded RAG Chat answer for question: '{}'", request.getQuery());
        PageResponse<SearchResultDto> page = hybridSearchService.searchHybrid(request);
        List<SearchResultDto> results = page != null ? page.getContent() : Collections.emptyList();

        String context = contextBuilder.buildContext(results);
        String prompt = promptBuilder.buildPrompt(request.getQuery(), context);
        String answer = llmProvider.generateAnswer(prompt);

        return Map.of(
                "question", request.getQuery(),
                "answer", answer,
                "provider", llmProvider.getProviderName(),
                "sources", results,
                "tokenCount", answer.split("\\s+").length
        );
    }

    public SseEmitter streamGroundedChat(SearchRequest request) {
        log.info("Streaming Grounded RAG Chat via SSE for question: '{}'", request.getQuery());
        SseEmitter emitter = new SseEmitter(60000L); // 60s timeout

        CompletableFuture.runAsync(() -> {
            try {
                // Step 1: Send Thinking event
                emitter.send(SseEmitter.event().name("thinking").data("Analyzing question & executing Hybrid Retrieval..."));

                // Step 2: Execute Hybrid Search
                PageResponse<SearchResultDto> page = hybridSearchService.searchHybrid(request);
                List<SearchResultDto> results = page != null ? page.getContent() : Collections.emptyList();

                // Step 3: Send Sources event
                emitter.send(SseEmitter.event().name("sources").data(results));

                // Step 4: Build Prompt & Stream Tokens
                String context = contextBuilder.buildContext(results);
                String prompt = promptBuilder.buildPrompt(request.getQuery(), context);

                emitter.send(SseEmitter.event().name("thinking").data("Generating grounded answer with citations..."));

                llmProvider.streamAnswer(prompt, token -> {
                    try {
                        emitter.send(SseEmitter.event().name("token").data(token));
                    } catch (IOException e) {
                        log.warn("Error sending SSE token: {}", e.getMessage());
                    }
                });

                // Step 5: Send Complete event
                emitter.send(SseEmitter.event().name("complete").data("SUCCESS"));
                emitter.complete();

            } catch (Exception e) {
                log.error("Failed during SSE streaming copilot execution: {}", e.getMessage(), e);
                try {
                    emitter.send(SseEmitter.event().name("error").data("Streaming failure: " + e.getMessage()));
                } catch (IOException ignored) {}
                emitter.completeWithError(e);
            }
        });

        return emitter;
    }
}
