package com.atlas.keywordsearch.query;

import java.util.*;

public class Trie {

    private static class TrieNode {
        Map<Character, TrieNode> children = new HashMap<>();
        boolean isEndOfWord = false;
        int popularity = 0;
    }

    private final TrieNode root = new TrieNode();

    public synchronized void insert(String word, int popularity) {
        if (word == null || word.isBlank()) return;
        TrieNode current = root;
        String normalized = word.trim().toLowerCase();
        for (char ch : normalized.toCharArray()) {
            current = current.children.computeIfAbsent(ch, c -> new TrieNode());
        }
        current.isEndOfWord = true;
        current.popularity = Math.max(current.popularity, popularity);
    }

    public synchronized List<String> searchPrefix(String prefix, int limit) {
        if (prefix == null || prefix.isBlank()) return Collections.emptyList();
        TrieNode current = root;
        String normalized = prefix.trim().toLowerCase();
        for (char ch : normalized.toCharArray()) {
            current = current.children.get(ch);
            if (current == null) {
                return Collections.emptyList();
            }
        }

        List<SuggestionEntry> results = new ArrayList<>();
        collectSuggestions(current, new StringBuilder(normalized), results);
        results.sort((a, b) -> Integer.compare(b.popularity, a.popularity));

        List<String> suggestions = new ArrayList<>();
        for (int i = 0; i < Math.min(limit, results.size()); i++) {
            suggestions.add(results.get(i).word);
        }
        return suggestions;
    }

    private void collectSuggestions(TrieNode node, StringBuilder prefix, List<SuggestionEntry> results) {
        if (node.isEndOfWord) {
            results.add(new SuggestionEntry(prefix.toString(), node.popularity));
        }
        for (Map.Entry<Character, TrieNode> entry : node.children.entrySet()) {
            prefix.append(entry.getKey());
            collectSuggestions(entry.getValue(), prefix, results);
            prefix.deleteCharAt(prefix.length() - 1);
        }
    }

    private record SuggestionEntry(String word, int popularity) {}
}
