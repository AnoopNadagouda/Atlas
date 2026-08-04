package com.atlas.indexbuilder.engine.analyzer;

import org.springframework.stereotype.Component;

@Component
public class PorterStemmer implements Stemmer {

    @Override
    public String stem(String word) {
        if (word == null || word.length() <= 2) return word;
        String w = word.toLowerCase();

        // Step 1a: plural suffixes
        if (w.endsWith("sses")) {
            w = w.substring(0, w.length() - 2);
        } else if (w.endsWith("ies")) {
            w = w.substring(0, w.length() - 2);
        } else if (w.endsWith("ches") || w.endsWith("shes") || w.endsWith("oxes") || w.endsWith("axes")) {
            w = w.substring(0, w.length() - 2);
        } else if (w.endsWith("ss")) {
            // Keep ss
        } else if (w.endsWith("s")) {
            w = w.substring(0, w.length() - 1);
        }

        // Step 1b: -ing, -ed, -ly, -ment
        if (w.endsWith("ing") && w.length() > 5) {
            w = w.substring(0, w.length() - 3);
        } else if (w.endsWith("ed") && w.length() > 4) {
            w = w.substring(0, w.length() - 2);
        } else if (w.endsWith("ly") && w.length() > 4) {
            w = w.substring(0, w.length() - 2);
        } else if (w.endsWith("ment") && w.length() > 6) {
            w = w.substring(0, w.length() - 4);
        }

        return w;
    }
}
