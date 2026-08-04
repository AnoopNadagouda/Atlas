package com.atlas.keywordsearch.query;

import org.springframework.stereotype.Component;

import java.text.Normalizer;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Component
public class QueryNormalizer {

    private static final Set<String> STOP_WORDS = Set.of(
            "a", "an", "and", "are", "as", "at", "be", "by", "for", "from", "has", "he", "in", "is", "it",
            "its", "of", "on", "that", "the", "to", "was", "were", "will", "with"
    );

    private static final Pattern TOKEN_PATTERN = Pattern.compile("[\\p{L}\\p{N}]+(?:['\\-][\\p{L}\\p{N}]+)*");

    public String normalizeSingleTerm(String term) {
        if (term == null || term.isBlank()) return "";
        String norm = Normalizer.normalize(term, Normalizer.Form.NFC).toLowerCase(Locale.ENGLISH).trim();
        if (norm.length() < 2 || STOP_WORDS.contains(norm)) return "";

        // Simple Stemming matching Phase 1.4
        if (wEndsWith(norm, "sses") || wEndsWith(norm, "ies")) return norm.substring(0, norm.length() - 2);
        if (wEndsWith(norm, "ches") || wEndsWith(norm, "shes") || wEndsWith(norm, "oxes")) return norm.substring(0, norm.length() - 2);
        if (wEndsWith(norm, "ing") && norm.length() > 5) return norm.substring(0, norm.length() - 3);
        if (wEndsWith(norm, "ed") && norm.length() > 4) return norm.substring(0, norm.length() - 2);
        if (wEndsWith(norm, "s") && !norm.endsWith("ss") && norm.length() > 3) return norm.substring(0, norm.length() - 1);
        return norm;
    }

    private boolean wEndsWith(String str, String suffix) {
        return str.endsWith(suffix);
    }

    public List<String> normalizeText(String text) {
        List<String> result = new ArrayList<>();
        if (text == null || text.isBlank()) return result;

        Matcher matcher = TOKEN_PATTERN.matcher(text);
        while (matcher.find()) {
            String term = normalizeSingleTerm(matcher.group());
            if (!term.isEmpty()) {
                result.add(term);
            }
        }
        return result;
    }
}
