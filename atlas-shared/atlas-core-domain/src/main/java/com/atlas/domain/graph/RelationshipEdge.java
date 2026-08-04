package com.atlas.domain.graph;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Map;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RelationshipEdge {
    private String id;
    private String sourceEntityId;
    private String targetEntityId;
    private RelationType relationType;
    private double confidenceScore;
    private String sourceDocumentId;
    private Map<String, Object> metadata;
}
