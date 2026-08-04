package com.atlas.indexbuilder.engine.analyzer;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class StopWordFilterTest {

    private StopWordFilter filter;

    @BeforeEach
    void setUp() {
        filter = new StopWordFilter();
    }

    @Test
    void testStopWordFiltering() {
        assertTrue(filter.isStopWord("the"));
        assertTrue(filter.isStopWord("and"));
        assertTrue(filter.isStopWord("with"));
        assertFalse(filter.isStopWord("atlas"));
        assertFalse(filter.isStopWord("search"));
    }
}
