package com.atlas.indexbuilder.engine.index;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class InvertedIndexMemoryTest {

    private InvertedIndexMemory indexMemory;

    @BeforeEach
    void setUp() {
        indexMemory = new InvertedIndexMemory();
    }

    @Test
    void testInvertedIndexPostingGeneration() {
        indexMemory.addTerm("atlas", "doc-1", 0, FieldType.TITLE);
        indexMemory.addTerm("search", "doc-1", 1, FieldType.BODY);
        indexMemory.addTerm("atlas", "doc-2", 0, FieldType.BODY);

        assertEquals(2, indexMemory.getDocumentCount());
        assertEquals(2, indexMemory.getVocabularySize());
        assertEquals(3, indexMemory.getTotalTermCount());

        PostingList postingList = indexMemory.getDictionary().get("atlas");
        assertNotNull(postingList);
        assertEquals(2, postingList.getDocumentFrequency());
        assertEquals(2, postingList.getCollectionFrequency());
        assertEquals(2, postingList.getPostings().size());

        Posting p1 = postingList.getPostings().get(0);
        assertEquals("doc-1", p1.getDocId());
        assertTrue(p1.getFieldFlags().contains(FieldType.TITLE));
    }
}
