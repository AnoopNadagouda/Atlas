package com.atlas.domain.graph;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Map;
import java.util.Set;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class EntityNode {
    private String id;
    private String name;
    private String canonicalName;
    private EntityType type;
    private Set<String> aliases;
    private double confidenceScore;
    private Map<String, Object> attributes;
}
