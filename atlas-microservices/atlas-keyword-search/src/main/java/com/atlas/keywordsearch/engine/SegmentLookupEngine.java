package com.atlas.keywordsearch.engine;

import com.atlas.common.utils.JsonUtils;
import com.atlas.keywordsearch.config.SearchProperties;
import com.atlas.keywordsearch.query.ParsedQuery;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.*;

@Slf4j
@Component
@RequiredArgsConstructor
public class SegmentLookupEngine {

    private final SearchProperties properties;

    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class PostingEntry {
        private String docId;
        private int termFrequency;
        private List<Integer> positions;
        private Set<String> fieldFlags;
    }

    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class PostingListEntry {
        private String term;
        private int documentFrequency;
        private int collectionFrequency;
        private List<PostingEntry> postings;
    }

    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class LookupResult {
        private Map<String, Map<String, PostingEntry>> termDocPostings; // term -> (docId -> PostingEntry)
        private Map<String, Integer> termDocFrequencies; // term -> df
        private int totalCollectionDocs;
        private double averageDocLength;
        private Map<String, Integer> docLengths; // docId -> docLength
    }

    public LookupResult lookup(ParsedQuery query) {
        Map<String, Map<String, PostingEntry>> termDocPostings = new HashMap<>();
        Map<String, Integer> termDocFrequencies = new HashMap<>();
        Map<String, Integer> docLengths = new HashMap<>();
        int totalCollectionDocs = 0;
        long totalLengthSum = 0;

        File storageDir = new File(properties.getStoragePath());
        if (!storageDir.exists() || !storageDir.isDirectory()) {
            return LookupResult.builder()
                    .termDocPostings(Collections.emptyMap())
                    .termDocFrequencies(Collections.emptyMap())
                    .totalCollectionDocs(0)
                    .averageDocLength(100.0)
                    .docLengths(Collections.emptyMap())
                    .build();
        }

        File[] segmentDirs = storageDir.listFiles(File::isDirectory);
        if (segmentDirs == null) segmentDirs = new File[0];

        for (File segDir : segmentDirs) {
            File postingsFile = new File(segDir, "postings.json");
            File dictFile = new File(segDir, "dict.json");

            if (!postingsFile.exists()) continue;

            try {
                String postingsJson = Files.readString(postingsFile.toPath());
                Map<String, Map<String, Object>> segPostings = JsonUtils.fromJson(postingsJson, Map.class);

                for (String term : query.getNormalizedTerms()) {
                    if (segPostings.containsKey(term)) {
                        Map<String, Object> postingListData = segPostings.get(term);
                        int df = ((Number) postingListData.getOrDefault("documentFrequency", 0)).intValue();
                        termDocFrequencies.put(term, termDocFrequencies.getOrDefault(term, 0) + df);

                        List<Map<String, Object>> postingsList = (List<Map<String, Object>>) postingListData.get("postings");
                        if (postingsList != null) {
                            Map<String, PostingEntry> docMap = termDocPostings.computeIfAbsent(term, k -> new HashMap<>());

                            for (Map<String, Object> p : postingsList) {
                                String docId = (String) p.get("docId");
                                int tf = ((Number) p.getOrDefault("termFrequency", 1)).intValue();
                                List<Integer> positions = (List<Integer>) p.getOrDefault("positions", List.of());
                                List<String> fields = (List<String>) p.getOrDefault("fieldFlags", List.of("BODY"));

                                PostingEntry entry = PostingEntry.builder()
                                        .docId(docId)
                                        .termFrequency(tf)
                                        .positions(positions)
                                        .fieldFlags(new HashSet<>(fields))
                                        .build();

                                docMap.put(docId, entry);

                                // Compute approximate doc length
                                int currentLen = docLengths.getOrDefault(docId, 0);
                                docLengths.put(docId, currentLen + tf);
                            }
                        }
                    }
                }
            } catch (Exception e) {
                log.error("Failed to read index segment at {}: {}", segDir.getAbsolutePath(), e.getMessage());
            }
        }

        totalCollectionDocs = docLengths.size();
        for (int len : docLengths.values()) {
            totalLengthSum += len;
        }
        double avgLength = totalCollectionDocs > 0 ? (double) totalLengthSum / totalCollectionDocs : 100.0;

        return LookupResult.builder()
                .termDocPostings(termDocPostings)
                .termDocFrequencies(termDocFrequencies)
                .totalCollectionDocs(totalCollectionDocs)
                .averageDocLength(avgLength)
                .docLengths(docLengths)
                .build();
    }
}
