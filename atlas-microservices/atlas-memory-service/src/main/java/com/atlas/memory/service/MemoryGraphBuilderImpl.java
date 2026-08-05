package com.atlas.memory.service;

import com.atlas.domain.memory.MemoryRelation;
import com.atlas.memory.entity.MemoryRelationEntity;
import com.atlas.memory.repository.MemoryRelationRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
public class MemoryGraphBuilderImpl implements MemoryGraphBuilder {

    private static final Logger log = LoggerFactory.getLogger(MemoryGraphBuilderImpl.class);
    private final MemoryRelationRepository relationRepository;

    public MemoryGraphBuilderImpl(MemoryRelationRepository relationRepository) {
        this.relationRepository = relationRepository;
    }

    @Override
    public MemoryRelation linkMemories(String sourceId, String targetId, String relationType, double weight) {
        log.info("[MemoryGraphBuilder] Linking memory '{}' -> '{}' ({})", sourceId, targetId, relationType);
        MemoryRelationEntity entity = new MemoryRelationEntity();
        entity.setId(UUID.randomUUID().toString());
        entity.setSourceMemoryId(sourceId);
        entity.setTargetMemoryId(targetId);
        entity.setRelationType(relationType != null ? relationType : "ASSOCIATED_WITH");
        entity.setWeight(weight > 0 ? weight : 1.0);
        entity.setCreatedAt(Instant.now());

        MemoryRelationEntity saved = relationRepository.save(entity);
        return mapToDomain(saved);
    }

    @Override
    public List<MemoryRelation> getRelationsForMemory(String memoryId) {
        List<MemoryRelationEntity> outgoing = relationRepository.findBySourceMemoryId(memoryId);
        List<MemoryRelationEntity> incoming = relationRepository.findByTargetMemoryId(memoryId);

        List<MemoryRelation> result = new ArrayList<>();
        outgoing.forEach(e -> result.add(mapToDomain(e)));
        incoming.forEach(e -> result.add(mapToDomain(e)));
        return result;
    }

    private MemoryRelation mapToDomain(MemoryRelationEntity entity) {
        MemoryRelation relation = new MemoryRelation();
        relation.setId(entity.getId());
        relation.setSourceMemoryId(entity.getSourceMemoryId());
        relation.setTargetMemoryId(entity.getTargetMemoryId());
        relation.setRelationType(entity.getRelationType());
        relation.setWeight(entity.getWeight());
        relation.setCreatedAt(entity.getCreatedAt());
        return relation;
    }
}
