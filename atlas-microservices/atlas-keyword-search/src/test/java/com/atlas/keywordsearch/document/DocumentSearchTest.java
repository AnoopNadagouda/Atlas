package com.atlas.keywordsearch.document;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

class DocumentSearchTest {

    private DocumentSearchService documentSearchService;

    @BeforeEach
    void setUp() {
        MultiModalDocumentService multiModalDocumentService = new MultiModalDocumentService();
        multiModalDocumentService.initSeedDocuments();
        documentSearchService = new DocumentSearchService(multiModalDocumentService, null, null, null, null);
    }

    @Test
    void testExecuteMultiModalSearch() {
        Map<String, Object> res = documentSearchService.executeMultiModalSearch("architecture", "HYBRID", "ALL");
        assertNotNull(res);
        assertEquals("architecture", res.get("query"));
        assertTrue((int) res.get("totalResults") >= 2);
        assertNotNull(res.get("searchIntegrations"));
    }
}
