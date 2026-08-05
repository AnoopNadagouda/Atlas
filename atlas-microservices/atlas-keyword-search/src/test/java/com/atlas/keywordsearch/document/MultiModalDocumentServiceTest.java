package com.atlas.keywordsearch.document;

import com.atlas.domain.document.UniversalDocument;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class MultiModalDocumentServiceTest {

    private MultiModalDocumentService documentService;

    @BeforeEach
    void setUp() {
        documentService = new MultiModalDocumentService();
        documentService.initSeedDocuments();
    }

    @Test
    void testGetAllDocuments() {
        List<UniversalDocument> list = documentService.getAllDocuments();
        assertNotNull(list);
        assertTrue(list.size() >= 2);
    }
}
