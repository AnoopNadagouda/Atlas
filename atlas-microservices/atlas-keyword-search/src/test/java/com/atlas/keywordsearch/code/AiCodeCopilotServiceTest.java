package com.atlas.keywordsearch.code;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class AiCodeCopilotServiceTest {

    private AiCodeCopilotService codeCopilotService;

    @BeforeEach
    void setUp() {
        AstSymbolExtractor extractor = new AstSymbolExtractor();
        CodeIndexBuilder indexBuilder = new CodeIndexBuilder(extractor);
        indexBuilder.initSeedRepositories();

        codeCopilotService = new AiCodeCopilotService(indexBuilder);
    }

    @Test
    void testExplainCodeSymbol() {
        String explanation = codeCopilotService.explainCodeSymbol("HybridSearchService");
        assertNotNull(explanation);
        assertTrue(explanation.contains("HybridSearchService"));
    }
}
