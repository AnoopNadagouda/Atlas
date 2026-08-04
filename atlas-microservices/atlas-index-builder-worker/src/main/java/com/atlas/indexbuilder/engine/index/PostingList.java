package com.atlas.indexbuilder.engine.index;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PostingList {
    private String term;
    @Builder.Default
    private int documentFrequency = 0;
    @Builder.Default
    private int collectionFrequency = 0;
    @Builder.Default
    private List<Posting> postings = new ArrayList<>();

    public void addOccurrences(String docId, List<Integer> positions, FieldType field) {
        Optional<Posting> existing = postings.stream().filter(p -> p.getDocId().equals(docId)).findFirst();
        Posting posting;
        if (existing.isPresent()) {
            posting = existing.get();
        } else {
            posting = Posting.builder().docId(docId).build();
            postings.add(posting);
            this.documentFrequency++;
        }
        for (int pos : positions) {
            posting.addPosition(pos, field);
            this.collectionFrequency++;
        }
    }
}
