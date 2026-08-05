package com.atlas.keywordsearch.code;

import com.atlas.domain.code.CodeSymbol;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class CodeIndexBuilderTest {

    private CodeIndexBuilder indexBuilder;

    @BeforeEach
    void setUp() {
        AstSymbolExtractor extractor = new AstSymbolExtractor();
        indexBuilder = new CodeIndexBuilder(extractor);
        indexBuilder.initSeedRepositories();
    }

    @Test
    void testSearchSymbols() {
        List<CodeSymbol> symbols = indexBuilder.searchSymbols("HybridSearchService");
        assertNotNull(symbols);
        assertFalse(symbols.isEmpty());
        assertEquals("HybridSearchService", symbols.get(0).getName());
    }
}
