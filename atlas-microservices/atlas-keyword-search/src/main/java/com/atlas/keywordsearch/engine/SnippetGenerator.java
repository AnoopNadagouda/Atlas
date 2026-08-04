package com.atlas.keywordsearch.engine;

import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Component
public class SnippetGenerator {

    private static final int MAX_SNIPPET_LENGTH = 180;

    public String generateSnippet(String text, Set<String> queryTerms) {
        if (text == null || text.isBlank()) {
            return "";
        }
        if (queryTerms == null || queryTerms.isEmpty()) {
            return text.length() > MAX_SNIPPET_LENGTH ? text.substring(0, MAX_SNIPPET_LENGTH) + "..." : text;
        }

        // Find earliest occurrence of any query term
        int bestStartIndex = 0;
        String lowerText = text.toLowerCase();
        int firstOccur = -1;

        for (String term : queryTerms) {
            int idx = lowerText.indexOf(term.toLowerCase());
            if (idx != -1 && (firstOccur == -1 || idx < firstOccur)) {
                firstOccur = idx;
            }
        }

        if (firstOccur > 20) {
            bestStartIndex = firstOccur - 20;
        }

        int endIndex = Math.min(text.length(), bestStartIndex + MAX_SNIPPET_LENGTH);
        String rawSnippet = text.substring(bestStartIndex, endIndex);

        if (bestStartIndex > 0) {
            rawSnippet = "..." + rawSnippet;
        }
        if (endIndex < text.length()) {
            rawSnippet = rawSnippet + "...";
        }

        // Highlight matched terms with <b>term</b>
        String highlightedSnippet = rawSnippet;
        for (String term : queryTerms) {
            if (term.length() < 2) continue;
            Pattern pattern = Pattern.compile("(?i)\\b(" + Pattern.quote(term) + ")\\b");
            Matcher matcher = pattern.matcher(highlightedSnippet);
            highlightedSnippet = matcher.replaceAll("<b>$1</b>");
        }

        return highlightedSnippet;
    }
}
