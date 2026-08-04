package com.atlas.keywordsearch.query;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.*;

@Slf4j
@Service
public class QueryRewriteService {

    private final Map<String, String> synonymMap = Map.of(
            "ai", "artificial intelligence",
            "db", "database",
            "js", "javascript",
            "spring", "spring boot",
            "kafka", "apache kafka"
    );

    public String rewriteQuery(String query) {
        if (query == null || query.isBlank()) return query;

        String[] tokens = query.trim().split("\\s+");
        StringBuilder rewritten = new StringBuilder();

        for (int i = 0; i < tokens.length; i++) {
            String token = tokens[i].toLowerCase();
            if (synonymMap.containsKey(token)) {
                rewritten.append(synonymMap.get(token));
            } else {
                rewritten.append(tokens[i]);
            }
            if (i < tokens.length - 1) {
                rewritten.append(" ");
            }
        }
        return rewritten.toString();
    }

    public List<String> getSynonyms(String token) {
        if (token == null) return Collections.emptyList();
        String syn = synonymMap.get(token.toLowerCase());
        return syn != null ? List.of(syn) : Collections.emptyList();
    }
}
