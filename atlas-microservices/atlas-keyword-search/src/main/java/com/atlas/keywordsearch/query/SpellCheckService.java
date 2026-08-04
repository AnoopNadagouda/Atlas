package com.atlas.keywordsearch.query;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.*;

@Slf4j
@Service
public class SpellCheckService {

    private final Set<String> dictionary = Set.of(
            "atlas", "search", "engine", "spring", "boot", "apache", "kafka",
            "postgresql", "database", "redis", "hybrid", "semantic", "pagerank",
            "vector", "hnsw", "reciprocal", "fusion", "crawler", "parser"
    );

    public String correctSpelling(String query) {
        if (query == null || query.isBlank()) return query;

        String[] tokens = query.trim().split("\\s+");
        StringBuilder corrected = new StringBuilder();

        for (int i = 0; i < tokens.length; i++) {
            String token = tokens[i].toLowerCase();
            if (dictionary.contains(token)) {
                corrected.append(tokens[i]);
            } else {
                String candidate = findBestCandidate(token);
                corrected.append(candidate != null ? candidate : tokens[i]);
            }
            if (i < tokens.length - 1) {
                corrected.append(" ");
            }
        }
        return corrected.toString();
    }

    private String findBestCandidate(String target) {
        String bestMatch = null;
        int minDistance = Integer.MAX_VALUE;

        for (String dictWord : dictionary) {
            int dist = computeLevenshteinDistance(target, dictWord);
            if (dist <= 2 && dist < minDistance) {
                minDistance = dist;
                bestMatch = dictWord;
            }
        }
        return bestMatch;
    }

    public static int computeLevenshteinDistance(String s1, String s2) {
        int m = s1.length();
        int n = s2.length();
        int[][] dp = new int[m + 1][n + 1];

        for (int i = 0; i <= m; i++) dp[i][0] = i;
        for (int j = 0; j <= n; j++) dp[0][j] = j;

        for (int i = 1; i <= m; i++) {
            for (int j = 1; j <= n; j++) {
                if (s1.charAt(i - 1) == s2.charAt(j - 1)) {
                    dp[i][j] = dp[i - 1][j - 1];
                } else {
                    dp[i][j] = 1 + Math.min(dp[i - 1][j - 1], Math.min(dp[i - 1][j], dp[i][j - 1]));
                }
            }
        }
        return dp[m][n];
    }
}
