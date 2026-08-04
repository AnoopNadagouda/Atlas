package com.atlas.indexbuilder.engine.analyzer;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class TokenizerTest {

    private Tokenizer tokenizer;

    @BeforeEach
    void setUp() {
        tokenizer = new Tokenizer();
    }

    @Test
    void testTokenizationWithHyphensAndNumbers() {
        String text = "Atlas cloud-native search engine processed 10000 documents in 2026!";
        List<Tokenizer.RawToken> tokens = tokenizer.tokenize(text);

        assertFalse(tokens.isEmpty());
        assertEquals("Atlas", tokens.get(0).getTerm());
        assertEquals(0, tokens.get(0).getPosition());
        assertEquals("cloud-native", tokens.get(1).getTerm());
        assertEquals(1, tokens.get(1).getPosition());
        assertEquals("10000", tokens.get(5).getTerm());
    }
}
