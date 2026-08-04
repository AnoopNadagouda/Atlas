package com.atlas.keywordsearch.query;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class TrieTest {

    private Trie trie;

    @BeforeEach
    void setUp() {
        trie = new Trie();
        trie.insert("atlas search", 100);
        trie.insert("atlas engine", 90);
        trie.insert("apache kafka", 80);
    }

    @Test
    void testPrefixSearch() {
        List<String> suggestions = trie.searchPrefix("atl", 5);
        assertEquals(2, suggestions.size());
        assertEquals("atlas search", suggestions.get(0));
    }
}
