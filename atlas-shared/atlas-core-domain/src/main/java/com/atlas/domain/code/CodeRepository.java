package com.atlas.domain.code;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Map;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CodeRepository {
    private String id;
    private String name;
    private String url;
    private String defaultBranch;
    private String commitHash;
    private long fileCount;
    private long symbolCount;
    private Map<String, Double> languageDistribution;
}
