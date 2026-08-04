package com.atlas.keywordsearch.query;

import com.atlas.domain.query.QueryIntent;
import org.springframework.stereotype.Component;

@Component
public class QueryIntentClassifier {

    public QueryIntent classifyIntent(String query) {
        if (query == null || query.isBlank()) return QueryIntent.INFORMATIONAL;

        String lower = query.toLowerCase().trim();

        if (lower.startsWith("what") || lower.startsWith("how") || lower.startsWith("why") || lower.contains("?")) {
            return QueryIntent.QUESTION;
        }

        if (lower.startsWith("http://") || lower.startsWith("https://") || lower.contains(".com") || lower.contains(".org")) {
            return QueryIntent.NAVIGATIONAL;
        }

        if (lower.contains("buy") || lower.contains("download") || lower.contains("price")) {
            return QueryIntent.TRANSACTIONAL;
        }

        if (lower.contains("class") || lower.contains("function") || lower.contains("code") || lower.contains("import")) {
            return QueryIntent.CODE_SEARCH;
        }

        if (lower.contains("atlas") || lower.contains("spring") || lower.contains("kafka")) {
            return QueryIntent.ENTITY_LOOKUP;
        }

        return QueryIntent.INFORMATIONAL;
    }
}
