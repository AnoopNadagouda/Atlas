package com.atlas.indexbuilder.engine.analyzer;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
@RequiredArgsConstructor
public class TextAnalyzer {

    private final Tokenizer tokenizer;
    private final NormalizerEngine normalizer;
    private final StopWordFilter stopWordFilter;
    private final PorterStemmer stemmer;

    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class AnalyzedToken {
        private String term;
        private int position;
    }

    public List<AnalyzedToken> analyze(String text) {
        List<AnalyzedToken> result = new ArrayList<>();
        if (text == null || text.isBlank()) return result;

        List<Tokenizer.RawToken> rawTokens = tokenizer.tokenize(text);
        for (Tokenizer.RawToken raw : rawTokens) {
            String norm = normalizer.normalize(raw.getTerm());
            if (norm.length() < 2 || stopWordFilter.isStopWord(norm)) {
                continue; // Filter out single char & stop words
            }
            String stemmed = stemmer.stem(norm);
            result.add(new AnalyzedToken(stemmed, raw.getPosition()));
        }
        return result;
    }
}
