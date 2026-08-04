package com.atlas.domain.ranking;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class LinkEdge {
    private String sourceDocId;
    private String targetDocId;
    private double weight;
}
