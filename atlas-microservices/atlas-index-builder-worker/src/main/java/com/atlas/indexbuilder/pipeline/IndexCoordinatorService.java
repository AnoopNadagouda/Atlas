package com.atlas.indexbuilder.pipeline;

import com.atlas.common.dto.IndexSegmentDto;
import com.atlas.common.dto.IndexStatisticsDto;
import com.atlas.common.dto.PageResponse;
import com.atlas.domain.exception.ResourceNotFoundException;
import com.atlas.kafka.events.CleanedDocumentEvent;
import com.atlas.indexbuilder.config.IndexProperties;
import com.atlas.indexbuilder.engine.analyzer.TextAnalyzer;
import com.atlas.indexbuilder.engine.index.FieldType;
import com.atlas.indexbuilder.engine.index.InvertedIndexMemory;
import com.atlas.indexbuilder.engine.index.SegmentWriter;
import com.atlas.indexbuilder.entity.DocumentSegmentMappingEntity;
import com.atlas.indexbuilder.entity.IndexSegmentEntity;
import com.atlas.indexbuilder.entity.IndexStatisticsEntity;
import com.atlas.indexbuilder.repository.DocumentSegmentMappingRepository;
import com.atlas.indexbuilder.repository.IndexSegmentRepository;
import com.atlas.indexbuilder.repository.IndexStatisticsRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.time.Instant;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class IndexCoordinatorService {

    private final IndexProperties properties;
    private final TextAnalyzer textAnalyzer;
    private final InvertedIndexMemory memoryIndex;
    private final SegmentWriter segmentWriter;
    private final IndexSegmentRepository segmentRepository;
    private final IndexStatisticsRepository statisticsRepository;
    private final DocumentSegmentMappingRepository mappingRepository;

    @Transactional
    public synchronized void indexDocument(CleanedDocumentEvent event) {
        if (event == null || event.getDocumentId() == null || event.isDuplicate()) {
            log.info("Skipping document indexing for docId '{}': duplicate or null", event != null ? event.getDocumentId() : "null");
            return;
        }

        log.info("Indexing document ID '{}' for URL: {}", event.getDocumentId(), event.getNormalizedUrl());

        // 1. Analyze Title Field
        if (event.getTitle() != null) {
            List<TextAnalyzer.AnalyzedToken> titleTokens = textAnalyzer.analyze(event.getTitle());
            for (TextAnalyzer.AnalyzedToken tok : titleTokens) {
                memoryIndex.addTerm(tok.getTerm(), event.getDocumentId(), tok.getPosition(), FieldType.TITLE);
            }
        }

        // 2. Analyze Headings Field
        if (event.getHeadings() != null) {
            for (String h : event.getHeadings()) {
                List<TextAnalyzer.AnalyzedToken> hTokens = textAnalyzer.analyze(h);
                for (TextAnalyzer.AnalyzedToken tok : hTokens) {
                    memoryIndex.addTerm(tok.getTerm(), event.getDocumentId(), tok.getPosition(), FieldType.HEADING);
                }
            }
        }

        // 3. Analyze Body Text Field
        if (event.getCleanText() != null) {
            List<TextAnalyzer.AnalyzedToken> bodyTokens = textAnalyzer.analyze(event.getCleanText());
            for (TextAnalyzer.AnalyzedToken tok : bodyTokens) {
                memoryIndex.addTerm(tok.getTerm(), event.getDocumentId(), tok.getPosition(), FieldType.BODY);
            }
        }

        // Trigger batch flush if batch threshold reached
        if (memoryIndex.getDocumentCount() >= properties.getBatchSize()) {
            try {
                flushSegment();
            } catch (IOException e) {
                log.error("Failed to auto-flush index segment: {}", e.getMessage(), e);
            }
        }
    }

    @Transactional
    public synchronized IndexSegmentDto flushSegment() throws IOException {
        if (memoryIndex.getDocumentCount() == 0) {
            log.info("Memory index is empty. No segment created.");
            return null;
        }

        SegmentWriter.SegmentMeta meta = segmentWriter.writeSegment(memoryIndex);

        IndexSegmentEntity entity = IndexSegmentEntity.builder()
                .id(meta.getSegmentId())
                .segmentName(meta.getSegmentName())
                .documentCount(meta.getDocumentCount())
                .vocabularySize(meta.getVocabularySize())
                .totalTermCount(meta.getTotalTermCount())
                .storagePath(meta.getStoragePath())
                .status("ACTIVE")
                .createdAt(meta.getCreatedAt())
                .build();

        segmentRepository.save(entity);

        // Record Document-Segment Mappings
        for (String docId : memoryIndex.getIndexedDocIds()) {
            DocumentSegmentMappingEntity mapping = DocumentSegmentMappingEntity.builder()
                    .docId(docId)
                    .segmentId(meta.getSegmentId())
                    .indexedAt(Instant.now())
                    .build();
            mappingRepository.save(mapping);
        }

        // Update overall collection statistics
        updateCollectionStatistics(meta);

        // Reset in-memory index
        memoryIndex.clear();

        return mapToSegmentDto(entity);
    }

    private void updateCollectionStatistics(SegmentWriter.SegmentMeta meta) {
        IndexStatisticsEntity stats = statisticsRepository.findById("global_stats")
                .orElse(IndexStatisticsEntity.builder().id("global_stats").build());

        stats.setTotalDocumentsIndexed(stats.getTotalDocumentsIndexed() + meta.getDocumentCount());
        stats.setTotalTermsIndexed(stats.getTotalTermsIndexed() + meta.getTotalTermCount());
        stats.setVocabularySize(Math.max(stats.getVocabularySize(), meta.getVocabularySize()));
        stats.setTotalSegmentsCount(stats.getTotalSegmentsCount() + 1);
        stats.setUpdatedAt(Instant.now());

        statisticsRepository.save(stats);
    }

    public IndexStatisticsDto getStatistics() {
        IndexStatisticsEntity stats = statisticsRepository.findById("global_stats")
                .orElse(IndexStatisticsEntity.builder()
                        .id("global_stats")
                        .totalDocumentsIndexed(0)
                        .totalTermsIndexed(0)
                        .vocabularySize(0)
                        .totalSegmentsCount(0)
                        .build());

        return IndexStatisticsDto.builder()
                .totalDocumentsIndexed(stats.getTotalDocumentsIndexed())
                .totalTermsIndexed(stats.getTotalTermsIndexed())
                .vocabularySize(stats.getVocabularySize())
                .totalSegmentsCount(stats.getTotalSegmentsCount())
                .storagePath(properties.getStoragePath())
                .build();
    }

    public PageResponse<IndexSegmentDto> getSegments(int page, int size) {
        Page<IndexSegmentEntity> pageResult = segmentRepository.findAll(PageRequest.of(page, size));
        List<IndexSegmentDto> content = pageResult.getContent().stream()
                .map(this::mapToSegmentDto)
                .toList();

        return PageResponse.<IndexSegmentDto>builder()
                .content(content)
                .pageNumber(pageResult.getNumber())
                .pageSize(pageResult.getSize())
                .totalElements(pageResult.getTotalElements())
                .totalPages(pageResult.getTotalPages())
                .last(pageResult.isLast())
                .build();
    }

    public IndexSegmentDto getSegment(String id) {
        IndexSegmentEntity entity = segmentRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("IndexSegment", id));
        return mapToSegmentDto(entity);
    }

    private IndexSegmentDto mapToSegmentDto(IndexSegmentEntity entity) {
        return IndexSegmentDto.builder()
                .segmentId(entity.getId())
                .segmentName(entity.getSegmentName())
                .documentCount(entity.getDocumentCount())
                .vocabularySize(entity.getVocabularySize())
                .totalTermCount(entity.getTotalTermCount())
                .storagePath(entity.getStoragePath())
                .status(entity.getStatus())
                .createdAt(entity.getCreatedAt())
                .build();
    }
}
