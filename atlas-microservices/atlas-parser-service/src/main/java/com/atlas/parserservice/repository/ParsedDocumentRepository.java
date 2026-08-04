package com.atlas.parserservice.repository;

import com.atlas.parserservice.entity.ParsedDocumentEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ParsedDocumentRepository extends JpaRepository<ParsedDocumentEntity, String> {
    Optional<ParsedDocumentEntity> findByContentHash(String contentHash);
    Optional<ParsedDocumentEntity> findByCanonicalUrl(String canonicalUrl);
    Page<ParsedDocumentEntity> findByIsDuplicateTrue(Pageable pageable);
    long countByIsDuplicateTrue();

    @Query("SELECT p FROM ParsedDocumentEntity p WHERE p.isDuplicate = false")
    List<ParsedDocumentEntity> findAllNonDuplicates();
}
