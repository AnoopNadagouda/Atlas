package com.atlas.keywordsearch.code;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class LanguageDetectorTest {

    private LanguageDetector detector;

    @BeforeEach
    void setUp() {
        detector = new LanguageDetector();
    }

    @Test
    void testDetectLanguage() {
        assertEquals("JAVA", detector.detectLanguage("Main.java"));
        assertEquals("PYTHON", detector.detectLanguage("app.py"));
        assertEquals("TYPESCRIPT", detector.detectLanguage("SearchPage.tsx"));
        assertEquals("JAVASCRIPT", detector.detectLanguage("index.js"));
        assertEquals("UNKNOWN", detector.detectLanguage("README.md"));
    }
}
