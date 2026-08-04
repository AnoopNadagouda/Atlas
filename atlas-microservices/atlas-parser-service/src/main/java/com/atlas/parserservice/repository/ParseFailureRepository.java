package com.atlas.parserservice.repository;

import com.atlas.parserservice.entity.ParseFailureEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ParseFailureRepository extends JpaRepository<ParseFailureEntity, String> {
    Page<ParseFailureEntity> findAll(Pageable pageable);
}
