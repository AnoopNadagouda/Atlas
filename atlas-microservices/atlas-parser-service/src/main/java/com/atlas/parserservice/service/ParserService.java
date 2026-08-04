package com.atlas.parserservice.service;

import com.atlas.common.dto.*;
import com.atlas.domain.exception.ResourceNotFoundException;
import com.atlas.parserservice.entity.ParseFailureEntity;
import com.atlas.parserservice.entity.ParsedDocumentEntity;
import com.atlas.parserservice.repository.ParseFailureRepository;
import com.atlas.parserservice.repository.ParsedDocumentRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class ParserService {

    private final ParsedDocumentRepository documentRepository;
    private final ParseFailureRepository failureRepository;

    public ParserStatisticsDto getStatistics() {
        long total = documentRepository.count();
        long duplicates = documentRepository.countByIsDuplicateTrue();
        long failures = failureRepository.count();

        List<ParsedDocumentEntity> allDocs = documentRepository.findAll();
        long exactCount = allDocs.stream().filter(d -> "EXACT".equalsIgnoreCase(d.getDuplicateType())).count();
        long simhashCount = allDocs.stream().filter(d -> "SIMHASH".equalsIgnoreCase(d.getDuplicateType())).count();
        long canonicalCount = allDocs.stream().filter(d -> "CANONICAL".equalsIgnoreCase(d.getDuplicateType())).count();

        double duplicateRate = total > 0 ? ((double) duplicates / total) * 100.0 : 0.0;

        return ParserStatisticsDto.builder()
                .totalProcessed(total)
                .exactDuplicates(exactCount)
                .simhashDuplicates(simhashCount)
                .canonicalDuplicates(canonicalCount)
                .totalDuplicates(duplicates)
                .duplicateRate(Math.round(duplicateRate * 100.0) / 100.0)
                .failuresCount(failures)
                .build();
    }

    public ParsedDocumentDto getDocument(String id) {
        ParsedDocumentEntity entity = documentRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("ParsedDocument", id));
        return mapToDocumentDto(entity);
    }

    public PageResponse<ParsedDocumentDto> getDuplicates(int page, int size) {
        Page<ParsedDocumentEntity> pageResult = documentRepository.findByIsDuplicateTrue(PageRequest.of(page, size));
        List<ParsedDocumentDto> content = pageResult.getContent().stream()
                .map(this::mapToDocumentDto)
                .toList();

        return PageResponse.<ParsedDocumentDto>builder()
                .content(content)
                .pageNumber(pageResult.getNumber())
                .pageSize(pageResult.getSize())
                .totalElements(pageResult.getTotalElements())
                .totalPages(pageResult.getTotalPages())
                .last(pageResult.isLast())
                .build();
    }

    public PageResponse<ParseFailureDto> getFailures(int page, int size) {
        Page<ParseFailureEntity> pageResult = failureRepository.findAll(PageRequest.of(page, size));
        List<ParseFailureDto> content = pageResult.getContent().stream()
                .map(this::mapToFailureDto)
                .toList();

        return PageResponse.<ParseFailureDto>builder()
                .content(content)
                .pageNumber(pageResult.getNumber())
                .pageSize(pageResult.getSize())
                .totalElements(pageResult.getTotalElements())
                .totalPages(pageResult.getTotalPages())
                .last(pageResult.isLast())
                .build();
    }

    private ParsedDocumentDto mapToDocumentDto(ParsedDocumentEntity entity) {
        String preview = entity.getCleanText() != null ? 
                (entity.getCleanText().length() > 200 ? entity.getCleanText().substring(0, 200) + "..." : entity.getCleanText()) : "";

        return ParsedDocumentDto.builder()
                .id(entity.getId())
                .jobId(entity.getJobId())
                .url(entity.getUrl())
                .normalizedUrl(entity.getNormalizedUrl())
                .canonicalUrl(entity.getCanonicalUrl())
                .domain(entity.getDomain())
                .title(entity.getTitle())
                .description(entity.getDescription())
                .cleanTextPreview(preview)
                .language(entity.getLanguage())
                .languageConfidence(entity.getLanguageConfidence())
                .isDuplicate(entity.isDuplicate())
                .duplicateType(entity.getDuplicateType())
                .duplicateOfDocId(entity.getDuplicateOfDocId())
                .simhash(entity.getSimhash())
                .contentHash(entity.getContentHash())
                .contentLength(entity.getContentLength())
                .processedAt(entity.getProcessedAt())
                .build();
    }

    private ParseFailureDto mapToFailureDto(ParseFailureEntity entity) {
        return ParseFailureDto.builder()
                .id(entity.getId())
                .docId(entity.getDocId())
                .url(entity.getUrl())
                .reason(entity.getReason())
                .errorMessage(entity.getErrorMessage())
                .failedAt(entity.getFailedAt())
                .build();
    }
}
