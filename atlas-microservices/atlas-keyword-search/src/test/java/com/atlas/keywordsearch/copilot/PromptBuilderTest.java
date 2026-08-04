package com.atlas.keywordsearch.copilot;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class PromptBuilderTest {

    private PromptBuilder promptBuilder;

    @BeforeEach
    void setUp() {
        promptBuilder = new PromptBuilder();
    }

    @Test
    void testPromptBuilderWithContext() {
        String prompt = promptBuilder.buildPrompt("What is Atlas?", "[1] Document ID: doc-1 | Title: Atlas Platform");

        assertNotNull(prompt);
        assertTrue(prompt.contains("User Question:"));
        assertTrue(prompt.contains("What is Atlas?"));
        assertTrue(prompt.contains("[1] Document ID: doc-1"));
        assertTrue(prompt.contains("Hallucination Guardrail"));
    }

    @Test
    void testPromptBuilderWithoutContextTriggersGuardrail() {
        String prompt = promptBuilder.buildPrompt("Unknown question", "");

        assertNotNull(prompt);
        assertTrue(prompt.contains("I couldn't find enough indexed information to answer confidently."));
    }
}
