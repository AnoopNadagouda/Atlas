package com.atlas.keywordsearch.document;

import com.atlas.domain.document.parser.ExtractionResult;
import com.atlas.domain.document.parser.ParserMetadata;
import com.atlas.keywordsearch.document.parser.DocxParser;
import com.atlas.keywordsearch.document.parser.PptxParser;
import com.atlas.keywordsearch.document.parser.XlsxParser;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayInputStream;

import static org.junit.jupiter.api.Assertions.*;

class OfficeParserTest {

    private DocxParser docxParser;
    private PptxParser pptxParser;
    private XlsxParser xlsxParser;

    @BeforeEach
    void setUp() {
        docxParser = new DocxParser();
        pptxParser = new PptxParser();
        xlsxParser = new XlsxParser();
    }

    @Test
    void testDocxParser() {
        ByteArrayInputStream stream = new ByteArrayInputStream("Word Document Specs".getBytes());
        ParserMetadata meta = ParserMetadata.builder().filename("doc.docx").fileType("DOCX").build();
        ExtractionResult res = docxParser.extract(stream, meta);
        assertNotNull(res);
        assertEquals("SUCCESS", res.getStatus());
    }

    @Test
    void testPptxParser() {
        ByteArrayInputStream stream = new ByteArrayInputStream("Slide 1 Overview".getBytes());
        ParserMetadata meta = ParserMetadata.builder().filename("deck.pptx").fileType("PPTX").build();
        ExtractionResult res = pptxParser.extract(stream, meta);
        assertNotNull(res);
        assertEquals(2, res.getSections().size());
    }

    @Test
    void testXlsxParser() {
        ByteArrayInputStream stream = new ByteArrayInputStream("Sheet1 Data".getBytes());
        ParserMetadata meta = ParserMetadata.builder().filename("sheet.xlsx").fileType("XLSX").build();
        ExtractionResult res = xlsxParser.extract(stream, meta);
        assertNotNull(res);
        assertEquals("SUCCESS", res.getStatus());
    }
}
