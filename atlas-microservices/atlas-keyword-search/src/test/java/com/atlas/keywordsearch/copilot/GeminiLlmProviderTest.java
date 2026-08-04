package com.atlas.keywordsearch.copilot;

import com.atlas.keywordsearch.config.GeminiProperties;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class GeminiLlmProviderTest {

    private GeminiLlmProvider provider;

    @BeforeEach
    void setUp() {
        GeminiProperties properties = new GeminiProperties();
        properties.setModel("gemini-1.5-flash");
        properties.setApiKey(""); // Empty key -> triggers local fallback

        GeminiRequestBuilder builder = new GeminiRequestBuilder(properties);
        LocalStubLlmProvider fallback = new LocalStubLlmProvider();

        provider = new GeminiLlmProvider(properties, builder, fallback);
    }

    @Test
    void testProviderNameAndHealth() {
        assertEquals("Google Gemini (gemini-1.5-flash)", provider.getProviderName());
        assertTrue(provider.isHealthy());
    }

    @Test
    void testFallbackWhenApiKeyIsMissing() {
        String answer = provider.generateAnswer("System: You are Atlas AI Search Copilot.\nQuestion: What is Atlas?\nContext Documents: [1] Document ID: doc-1");
        assertNotNull(answer);
        assertFalse(answer.isBlank());
    }
}
