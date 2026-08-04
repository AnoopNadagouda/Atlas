package com.atlas.keywordsearch.query;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class QueryRewriteServiceTest {

    private QueryRewriteService rewriteService;

    @BeforeEach
    void setUp() {
        rewriteService = new QueryRewriteService();
    }

    @Test
    void testSynonymExpansion() {
        String rewritten = rewriteService.rewriteQuery("ai search with db and kafka");
        assertEquals("artificial intelligence search with database and apache kafka", rewritten);
    }
}
