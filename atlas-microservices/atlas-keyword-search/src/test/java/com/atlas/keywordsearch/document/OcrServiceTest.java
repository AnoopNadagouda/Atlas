package com.atlas.keywordsearch.document;

import com.atlas.keywordsearch.document.ocr.OcrTaskQueue;
import com.atlas.keywordsearch.document.ocr.PdfImageExtractor;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

class OcrServiceTest {

    private OcrService ocrService;

    @BeforeEach
    void setUp() {
        ocrService = new OcrService();
    }

    @Test
    void testExtractTextFromImage() {
        Map<String, Object> result = ocrService.extractTextFromImage("scan.png", new byte[0]);
        assertNotNull(result);
        assertTrue((double) result.get("confidenceScore") > 0.90);
        assertEquals("en", result.get("detectedLanguage"));
    }

    @Test
    void testExtractScannedPdf() {
        List<PdfImageExtractor.PdfPageOcrResult> pageResults = ocrService.extractScannedPdf("scan.pdf", new byte[0]);
        assertNotNull(pageResults);
        assertFalse(pageResults.isEmpty());
        assertEquals(1, pageResults.get(0).getPageNumber());
    }

    @Test
    void testSubmitOcrJob() {
        OcrTaskQueue.OcrJobStatus job = ocrService.submitOcrJob("image.png", new byte[0]);
        assertNotNull(job);
        assertEquals("COMPLETED", job.getStatus());
    }
}
