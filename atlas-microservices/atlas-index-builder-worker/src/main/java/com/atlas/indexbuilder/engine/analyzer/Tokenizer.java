package com.atlas.indexbuilder.engine.analyzer;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Component
public class Tokenizer {

    private static final Pattern TOKEN_PATTERN = Pattern.compile("[\\p{L}\\p{N}]+(?:['\\-][\\p{L}\\p{N}]+)*");

    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class RawToken {
        private String term;
        private int position;
    }

    public List<RawToken> tokenize(String text) {
        List<RawToken> tokens = new ArrayList<>();
        if (text == null || text.isBlank()) return tokens;

        Matcher matcher = TOKEN_PATTERN.matcher(text);
        int pos = 0;
        while (matcher.find()) {
            String word = matcher.group();
            tokens.add(new RawToken(word, pos++));
        }
        return tokens;
    }
}
