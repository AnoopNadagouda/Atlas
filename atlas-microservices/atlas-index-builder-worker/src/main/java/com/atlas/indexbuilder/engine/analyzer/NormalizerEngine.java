package com.atlas.indexbuilder.engine.analyzer;

import org.springframework.stereotype.Component;

import java.text.Normalizer;
import java.util.Locale;

@Component
public class NormalizerEngine {

    public String normalize(String token) {
        if (token == null || token.isBlank()) return "";
        String normalized = Normalizer.normalize(token, Normalizer.Form.NFC);
        return normalized.toLowerCase(Locale.ENGLISH).trim();
    }
}
