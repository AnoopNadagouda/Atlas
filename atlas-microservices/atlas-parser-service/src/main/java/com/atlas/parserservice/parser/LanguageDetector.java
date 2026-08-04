package com.atlas.parserservice.parser;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Locale;

@Component
public class LanguageDetector {

    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class DetectionResult {
        private String language;
        private double confidence;
    }

    public DetectionResult detectLanguage(String text, String htmlLangHint) {
        if (htmlLangHint != null && !htmlLangHint.isBlank()) {
            String code = htmlLangHint.trim().toLowerCase(Locale.ENGLISH);
            if (code.contains("-")) code = code.split("-")[0];
            return DetectionResult.builder().language(code).confidence(0.95).build();
        }

        if (text == null || text.isBlank()) {
            return DetectionResult.builder().language("en").confidence(0.50).build();
        }

        // Simple heuristic token frequency analysis
        String lower = text.toLowerCase();
        int enScore = countOccurrences(lower, " the ", " is ", " and ", " of ", " to ", " in ");
        int esScore = countOccurrences(lower, " el ", " la ", " y ", " de ", " en ", " que ");
        int frScore = countOccurrences(lower, " le ", " la ", " et ", " de ", " dans ", " un ");
        int deScore = countOccurrences(lower, " der ", " die ", " das ", " und ", " in ", " den ");

        int maxScore = Math.max(enScore, Math.max(esScore, Math.max(frScore, deScore)));
        if (maxScore == 0) {
            return DetectionResult.builder().language("en").confidence(0.60).build();
        }

        String lang = "en";
        if (maxScore == esScore) lang = "es";
        else if (maxScore == frScore) lang = "fr";
        else if (maxScore == deScore) lang = "de";

        double confidence = Math.min(0.99, 0.60 + (maxScore * 0.05));
        return DetectionResult.builder().language(lang).confidence(confidence).build();
    }

    private int countOccurrences(String text, String... words) {
        int count = 0;
        for (String w : words) {
            int pos = 0;
            while ((pos = text.indexOf(w, pos)) != -1) {
                count++;
                pos += w.length();
            }
        }
        return count;
    }
}
