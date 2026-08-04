package com.atlas.indexbuilder.engine.index;

import com.atlas.indexbuilder.config.IndexProperties;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import java.io.File;
import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;

class SegmentWriterTest {

    @TempDir
    File tempDir;

    private SegmentWriter writer;
    private InvertedIndexMemory indexMemory;

    @BeforeEach
    void setUp() {
        IndexProperties properties = new IndexProperties();
        properties.setStoragePath(tempDir.getAbsolutePath());

        writer = new SegmentWriter(properties);
        indexMemory = new InvertedIndexMemory();
    }

    @Test
    void testSegmentDiskPersistence() throws IOException {
        indexMemory.addTerm("cloud", "doc-100", 0, FieldType.TITLE);
        indexMemory.addTerm("engine", "doc-100", 1, FieldType.BODY);

        SegmentWriter.SegmentMeta meta = writer.writeSegment(indexMemory);

        assertNotNull(meta);
        assertEquals(1, meta.getDocumentCount());
        assertEquals(2, meta.getVocabularySize());

        File segmentDir = new File(meta.getStoragePath());
        assertTrue(segmentDir.exists());
        assertTrue(new File(segmentDir, "dict.json").exists());
        assertTrue(new File(segmentDir, "postings.json").exists());
        assertTrue(new File(segmentDir, "segment_meta.json").exists());
    }
}
