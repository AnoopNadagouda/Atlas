package com.atlas.indexbuilder.engine.analyzer;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class PorterStemmerTest {

    private PorterStemmer stemmer;

    @BeforeEach
    void setUp() {
        stemmer = new PorterStemmer();
    }

    @Test
    void testStemmingRules() {
        assertEquals("process", stemmer.stem("processing"));
        assertEquals("crawl", stemmer.stem("crawled"));
        assertEquals("search", stemmer.stem("searches"));
    }
}
