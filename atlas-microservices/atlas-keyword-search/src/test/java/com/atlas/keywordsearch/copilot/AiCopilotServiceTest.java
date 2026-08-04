package com.atlas.keywordsearch.copilot;

import com.atlas.common.dto.PageResponse;
import com.atlas.common.dto.SearchRequest;
import com.atlas.common.dto.SearchResultDto;
import com.atlas.keywordsearch.config.AtlasCopilotProperties;
import com.atlas.keywordsearch.hybrid.HybridSearchService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

class AiCopilotServiceTest {

    private AiCopilotService aiCopilotService;

    @BeforeEach
    void setUp() {
        HybridSearchService mockHybridService = Mockito.mock(HybridSearchService.class);
        AtlasCopilotProperties copilotProperties = new AtlasCopilotProperties();
        ContextBuilder contextBuilder = new ContextBuilder(copilotProperties);
        PromptBuilder promptBuilder = new PromptBuilder();
        LocalStubLlmProvider llmProvider = new LocalStubLlmProvider();

        SearchResultDto doc = SearchResultDto.builder()
                .id("doc-1")
                .title("Atlas AI Search Engine")
                .url("https://atlas.search/1")
                .snippet("Enterprise search platform with RAG copilot.")
                .build();

        PageResponse<SearchResultDto> page = PageResponse.<SearchResultDto>builder()
                .content(List.of(doc))
                .totalElements(1)
                .build();

        when(mockHybridService.searchHybrid(any())).thenReturn(page);

        aiCopilotService = new AiCopilotService(mockHybridService, contextBuilder, promptBuilder, llmProvider);
    }

    @Test
    void testGroundedChatGeneration() {
        SearchRequest request = SearchRequest.builder()
                .query("What is Atlas?")
                .build();

        Map<String, Object> result = aiCopilotService.generateGroundedChat(request);

        assertNotNull(result);
        assertEquals("What is Atlas?", result.get("question"));
        assertNotNull(result.get("answer"));
        assertTrue(result.get("answer").toString().contains("Atlas"));
    }

    @Test
    void testSseStreamInitialization() {
        SearchRequest request = SearchRequest.builder()
                .query("Explain hybrid search")
                .build();

        SseEmitter emitter = aiCopilotService.streamGroundedChat(request);
        assertNotNull(emitter);
    }
}
