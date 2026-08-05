package com.atlas.keywordsearch.document;

import com.atlas.domain.document.MetadataRegistry;
import com.atlas.keywordsearch.document.metadata.ImageMetadataExtractor;
import com.atlas.keywordsearch.document.metadata.MediaMetadataExtractor;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class MediaMetadataTest {

    private ImageMetadataExtractor imageMetadataExtractor;
    private MediaMetadataExtractor mediaMetadataExtractor;

    @BeforeEach
    void setUp() {
        imageMetadataExtractor = new ImageMetadataExtractor();
        mediaMetadataExtractor = new MediaMetadataExtractor();
    }

    @Test
    void testExtractImageMetadata() {
        MetadataRegistry reg = imageMetadataExtractor.extractImageMetadata("diagram.png", new byte[0]);
        assertNotNull(reg);
        assertEquals("Canon", reg.getCameraMake());
        assertEquals(3840, reg.getWidth());
        assertNotNull(reg.getMd5Hash());
        assertNotNull(reg.getGpsLatitude());
    }

    @Test
    void testExtractMediaMetadata() {
        MetadataRegistry reg = mediaMetadataExtractor.extractMediaMetadata("video.mp4", "MP4", new byte[0]);
        assertNotNull(reg);
        assertEquals(184.5, reg.getDurationSeconds());
        assertTrue(reg.getCodec().contains("H.264"));
        assertNotNull(reg.getSubtitles());
    }
}
