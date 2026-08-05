package com.atlas.keywordsearch.document;

import com.atlas.domain.document.parser.ExtractionResult;
import com.atlas.domain.document.parser.ParserMetadata;
import com.atlas.keywordsearch.document.parser.PdfParser;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayInputStream;

import static org.junit.jupiter.api.Assertions.*;

class PdfParserTest {

    private PdfParser pdfParser;

    @BeforeEach
    void setUp() {
        pdfParser = new PdfParser();
    }

    @Test
    void testSupports() {
        assertTrue(pdfParser.supports("PDF", "application/pdf"));
        assertFalse(pdfParser.supports("DOCX", "application/msword"));
    }

    @Test
    void testPdfExtraction() {
        String mockPdfContent = "Atlas Search Engine Architecture and PDF Ingestion Specifications.";
        ByteArrayInputStream stream = new ByteArrayInputStream(mockPdfContent.getBytes());
        ParserMetadata metadata = ParserMetadata.builder()
                .filename("architecture.pdf")
                .fileType("PDF")
                .mimeType("application/pdf")
                .sizeBytes(mockPdfContent.length())
                .build();

        ExtractionResult result = pdfParser.extract(stream, metadata);

        assertNotNull(result);
        assertEquals("SUCCESS", result.getStatus());
        assertEquals("architecture.pdf", result.getDocumentTitle());
        assertTrue(result.getExtractedText().contains("Atlas Search Engine"));
        assertFalse(result.getSections().isEmpty());
    }
}
