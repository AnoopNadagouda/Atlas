package com.atlas.parserservice.parser;

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
    void testLanguageDetection() {
        var resEn = detector.detectLanguage("This is the main search index document pipeline for Atlas.", null);
        assertEquals("en", resEn.getLanguage());

        var resEs = detector.detectLanguage("El motor de búsqueda distribuido es muy rápido en la nube.", null);
        assertEquals("es", resEs.getLanguage());

        var resHint = detector.detectLanguage("Some content", "fr-FR");
        assertEquals("fr", resHint.getLanguage());
    }
}
