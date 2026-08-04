package com.atlas.keywordsearch.repository;

import com.atlas.keywordsearch.entity.DocumentEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface DocumentRepository extends JpaRepository<DocumentEntity, UUID> {
    Optional<DocumentEntity> findByUrlHash(String urlHash);
    Optional<DocumentEntity> findByUrl(String url);
}
