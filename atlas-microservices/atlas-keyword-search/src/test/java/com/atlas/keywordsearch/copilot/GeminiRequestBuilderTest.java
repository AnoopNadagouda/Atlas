package com.atlas.keywordsearch.copilot;

import com.atlas.domain.exception.AtlasException;
import com.atlas.keywordsearch.config.GeminiProperties;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

class GeminiRequestBuilderTest {

    private GeminiRequestBuilder requestBuilder;

    @BeforeEach
    void setUp() {
        GeminiProperties properties = new GeminiProperties();
        properties.setModel("gemini-1.5-flash");
        properties.setTemperature(0.2);
        properties.setTopP(0.95);
        properties.setTopK(40);
        properties.setMaxOutputTokens(1024);

        requestBuilder = new GeminiRequestBuilder(properties);
    }

    @Test
    void testValidGeminiRequestBody() {
        Map<String, Object> body = requestBuilder.buildRequestBody("What is Atlas?");

        assertNotNull(body);
        assertTrue(body.containsKey("contents"));
        assertTrue(body.containsKey("generationConfig"));

        @SuppressWarnings("unchecked")
        List<Map<String, Object>> contents = (List<Map<String, Object>>) body.get("contents");
        assertEquals(1, contents.size());
        assertEquals("user", contents.get(0).get("role"));
    }

    @Test
    void testNullOrEmptyPromptThrowsException() {
        assertThrows(AtlasException.class, () -> requestBuilder.buildRequestBody(null));
        assertThrows(AtlasException.class, () -> requestBuilder.buildRequestBody("   "));
    }
}
