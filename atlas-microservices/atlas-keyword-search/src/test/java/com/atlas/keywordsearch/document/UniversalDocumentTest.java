package com.atlas.keywordsearch.document;

import com.atlas.domain.document.*;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class UniversalDocumentTest {

    @Test
    void testUniversalDocumentBuilder() {
        UniversalDocument doc = UniversalDocument.builder()
                .documentId("doc-test-1")
                .title("Test Document")
                .fileType("PDF")
                .sections(List.of("Intro", "Details"))
                .structuredSections(List.of(
                        DocumentSection.builder().sectionId("sec-1").title("Intro").level(1).build()
                ))
                .attachments(List.of(
                        Attachment.builder().attachmentId("att-1").filename("figure1.png").build()
                ))
                .contentFragments(List.of(
                        ContentFragment.builder().fragmentId("frag-1").fragmentType("CODE").textContent("class Main {}").build()
                ))
                .metadataRegistry(MetadataRegistry.builder().cameraMake("Canon").width(1920).height(1080).build())
                .build();

        assertNotNull(doc);
        assertEquals("doc-test-1", doc.getDocumentId());
        assertEquals("PDF", doc.getFileType());
        assertEquals(2, doc.getSections().size());
        assertEquals(1, doc.getStructuredSections().size());
        assertEquals(1, doc.getAttachments().size());
        assertEquals(1, doc.getContentFragments().size());
        assertNotNull(doc.getMetadataRegistry());
        assertEquals("Canon", doc.getMetadataRegistry().getCameraMake());
    }
}
