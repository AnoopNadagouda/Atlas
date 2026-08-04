package com.atlas.indexbuilder.engine.index;

import lombok.Getter;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

@Component
public class InvertedIndexMemory {

    @Getter
    private final Map<String, PostingList> dictionary = new ConcurrentHashMap<>();
    @Getter
    private final Set<String> indexedDocIds = ConcurrentHashMap.newKeySet();

    public void addTerm(String term, String docId, int position, FieldType field) {
        if (term == null || term.isBlank()) return;

        indexedDocIds.add(docId);
        dictionary.computeIfAbsent(term, t -> PostingList.builder().term(t).build())
                .addOccurrences(docId, java.util.List.of(position), field);
    }

    public int getVocabularySize() {
        return dictionary.size();
    }

    public int getDocumentCount() {
        return indexedDocIds.size();
    }

    public int getTotalTermCount() {
        return dictionary.values().stream().mapToInt(PostingList::getCollectionFrequency).sum();
    }

    public void clear() {
        dictionary.clear();
        indexedDocIds.clear();
    }
}
