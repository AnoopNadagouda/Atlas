package com.atlas.keywordsearch.code;

import com.atlas.domain.code.CodeSymbol;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class AstSymbolExtractorTest {

    private AstSymbolExtractor extractor;

    @BeforeEach
    void setUp() {
        extractor = new AstSymbolExtractor();
    }

    @Test
    void testExtractJavaSymbols() {
        String code = "public class SearchEngine { public void executeQuery() {} }";
        List<CodeSymbol> symbols = extractor.extractSymbols(code, "JAVA", "repo1", "SearchEngine.java");
        assertNotNull(symbols);
        assertEquals(2, symbols.size());
        assertTrue(symbols.stream().anyMatch(s -> s.getName().equals("SearchEngine") && s.getType().equals("CLASS")));
        assertTrue(symbols.stream().anyMatch(s -> s.getName().equals("executeQuery") && s.getType().equals("METHOD")));
    }
}
