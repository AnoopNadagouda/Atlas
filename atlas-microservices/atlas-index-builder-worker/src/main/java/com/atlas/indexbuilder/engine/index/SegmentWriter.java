package com.atlas.indexbuilder.engine.index;

import com.atlas.common.utils.JsonUtils;
import com.atlas.indexbuilder.config.IndexProperties;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.time.Instant;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@Slf4j
@Component
@RequiredArgsConstructor
public class SegmentWriter {

    private final IndexProperties properties;

    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class SegmentMeta {
        private String segmentId;
        private String segmentName;
        private int documentCount;
        private int vocabularySize;
        private int totalTermCount;
        private String storagePath;
        private Instant createdAt;
    }

    public SegmentMeta writeSegment(InvertedIndexMemory memoryIndex) throws IOException {
        String segmentId = "seg-" + UUID.randomUUID().toString().substring(0, 8);
        String segmentName = "Segment-" + System.currentTimeMillis();
        File segmentDir = new File(properties.getStoragePath(), segmentId);
        if (!segmentDir.exists()) {
            segmentDir.mkdirs();
        }

        Map<String, Map<String, Integer>> dictMap = new HashMap<>();
        memoryIndex.getDictionary().forEach((term, postingList) -> {
            dictMap.put(term, Map.of(
                    "df", postingList.getDocumentFrequency(),
                    "cf", postingList.getCollectionFrequency()
            ));
        });

        // Write dict.json
        File dictFile = new File(segmentDir, "dict.json");
        try (FileWriter writer = new FileWriter(dictFile)) {
            writer.write(JsonUtils.toJson(dictMap));
        }

        // Write postings.json
        File postingsFile = new File(segmentDir, "postings.json");
        try (FileWriter writer = new FileWriter(postingsFile)) {
            writer.write(JsonUtils.toJson(memoryIndex.getDictionary()));
        }

        SegmentMeta meta = SegmentMeta.builder()
                .segmentId(segmentId)
                .segmentName(segmentName)
                .documentCount(memoryIndex.getDocumentCount())
                .vocabularySize(memoryIndex.getVocabularySize())
                .totalTermCount(memoryIndex.getTotalTermCount())
                .storagePath(segmentDir.getAbsolutePath())
                .createdAt(Instant.now())
                .build();

        // Write segment_meta.json
        File metaFile = new File(segmentDir, "segment_meta.json");
        try (FileWriter writer = new FileWriter(metaFile)) {
            writer.write(JsonUtils.toJson(meta));
        }

        log.info("Successfully flushed inverted index segment '{}' to disk at: {}", segmentId, segmentDir.getAbsolutePath());
        return meta;
    }
}
