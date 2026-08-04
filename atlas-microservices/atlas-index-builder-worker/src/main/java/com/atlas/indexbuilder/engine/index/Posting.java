package com.atlas.indexbuilder.engine.index;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Posting {
    private String docId;
    @Builder.Default
    private int termFrequency = 0;
    @Builder.Default
    private List<Integer> positions = new ArrayList<>();
    @Builder.Default
    private Set<FieldType> fieldFlags = new HashSet<>();

    public void addPosition(int pos, FieldType field) {
        this.positions.add(pos);
        this.termFrequency++;
        if (field != null) {
            this.fieldFlags.add(field);
        }
    }
}
