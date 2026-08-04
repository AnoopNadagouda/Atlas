package com.atlas.keywordsearch.query;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Component
@RequiredArgsConstructor
public class QueryParser {

    private final QueryNormalizer queryNormalizer;

    private static final Pattern PHRASE_PATTERN = Pattern.compile("\"([^\"]+)\"");

    public ParsedQuery parse(String queryText) {
        if (queryText == null || queryText.isBlank()) {
            return ParsedQuery.builder().rawQuery("").build();
        }

        ParsedQuery result = ParsedQuery.builder()
                .rawQuery(queryText)
                .build();

        String workText = queryText;

        // 1. Extract quoted phrases `"distributed search"`
        Matcher phraseMatcher = PHRASE_PATTERN.matcher(workText);
        while (phraseMatcher.find()) {
            String phrase = phraseMatcher.group(1);
            List<String> normalizedPhraseTokens = queryNormalizer.normalizeText(phrase);
            if (!normalizedPhraseTokens.isEmpty()) {
                result.getPhrases().add(normalizedPhraseTokens);
            }
        }
        workText = phraseMatcher.replaceAll(" ");

        // 2. Parse boolean operators AND, OR, NOT and terms
        String[] tokens = workText.split("\\s+");
        String currentOp = "SHOULD";

        for (String tok : tokens) {
            if (tok.equalsIgnoreCase("AND")) {
                currentOp = "MUST";
                continue;
            } else if (tok.equalsIgnoreCase("NOT")) {
                currentOp = "MUST_NOT";
                continue;
            } else if (tok.equalsIgnoreCase("OR")) {
                currentOp = "SHOULD";
                continue;
            }

            List<String> normList = queryNormalizer.normalizeText(tok);
            for (String norm : normList) {
                if (!result.getNormalizedTerms().contains(norm)) {
                    result.getNormalizedTerms().add(norm);
                }

                if ("MUST".equals(currentOp)) {
                    result.getMustTerms().add(norm);
                } else if ("MUST_NOT".equals(currentOp)) {
                    result.getMustNotTerms().add(norm);
                } else {
                    result.getShouldTerms().add(norm);
                }
            }
            currentOp = "SHOULD"; // Reset operator after token
        }

        return result;
    }
}
