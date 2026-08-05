package com.atlas.keywordsearch.code;

import org.springframework.stereotype.Component;

@Component
public class LanguageDetector {

    public String detectLanguage(String fileName) {
        if (fileName == null) return "UNKNOWN";
        String lower = fileName.toLowerCase();
        if (lower.endsWith(".java")) return "JAVA";
        if (lower.endsWith(".py")) return "PYTHON";
        if (lower.endsWith(".ts") || lower.endsWith(".tsx")) return "TYPESCRIPT";
        if (lower.endsWith(".js") || lower.endsWith(".jsx")) return "JAVASCRIPT";
        return "UNKNOWN";
    }
}
