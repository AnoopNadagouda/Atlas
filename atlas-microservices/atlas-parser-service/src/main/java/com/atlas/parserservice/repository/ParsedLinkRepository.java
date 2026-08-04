package com.atlas.parserservice.repository;

import com.atlas.parserservice.entity.ParsedLinkEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ParsedLinkRepository extends JpaRepository<ParsedLinkEntity, String> {
    List<ParsedLinkEntity> findByDocId(String docId);
}
