package com.atlas.keywordsearch.query;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class SpellCheckServiceTest {

    private SpellCheckService spellCheckService;

    @BeforeEach
    void setUp() {
        spellCheckService = new SpellCheckService();
    }

    @Test
    void testLevenshteinDistanceAndCorrection() {
        assertEquals(1, SpellCheckService.computeLevenshteinDistance("atls", "atlas"));
        String corrected = spellCheckService.correctSpelling("atls search");
        assertEquals("atlas search", corrected);
    }
}
