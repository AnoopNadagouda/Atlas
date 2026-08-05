package com.atlas.domain.history;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class VersionDiff {
    private String docId;
    private String fromVersionId;
    private String toVersionId;
    private List<String> addedContent;
    private List<String> removedContent;
    private double similarityScore;
}
