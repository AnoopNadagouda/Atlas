package com.atlas.keywordsearch.query;

import jakarta.annotation.PostConstruct;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
public class AutocompleteService {

    private final Trie trie = new Trie();

    @PostConstruct
    public void initSeedDictionary() {
        log.info("Pre-loading Autocomplete Trie with popular search terms...");
        trie.insert("atlas search engine", 100);
        trie.insert("apache kafka streaming", 90);
        trie.insert("spring boot microservices", 95);
        trie.insert("postgresql database", 85);
        trie.insert("redis caching", 80);
        trie.insert("bm25 ranking", 75);
        trie.insert("hnsw vector search", 70);
        trie.insert("reciprocal rank fusion", 65);
        trie.insert("pagerank algorithm", 60);
        log.info("Autocomplete Trie initialized successfully.");
    }

    public List<String> getSuggestions(String prefix, int limit) {
        return trie.searchPrefix(prefix, limit);
    }
}
