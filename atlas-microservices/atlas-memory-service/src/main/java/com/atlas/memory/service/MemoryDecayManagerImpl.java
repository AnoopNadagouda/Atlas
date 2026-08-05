package com.atlas.memory.service;

import com.atlas.domain.memory.MemoryState;
import com.atlas.memory.entity.MemoryEntity;
import com.atlas.memory.repository.MemoryRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.time.Instant;
import java.util.List;

@Service
public class MemoryDecayManagerImpl implements MemoryDecayManager {

    private static final Logger log = LoggerFactory.getLogger(MemoryDecayManagerImpl.class);

    @Value("${atlas.memory.default-decay-rate:0.05}")
    private double defaultDecayRate;

    private final MemoryRepository memoryRepository;

    public MemoryDecayManagerImpl(MemoryRepository memoryRepository) {
        this.memoryRepository = memoryRepository;
    }

    @Override
    public void applyDecay(List<MemoryEntity> memories) {
        if (memories == null || memories.isEmpty()) return;

        log.debug("[MemoryDecayManager] Applying Ebbinghaus decay function to {} memories", memories.size());
        for (MemoryEntity entity : memories) {
            double decay = computeDecayFactor(entity);
            entity.setDecayFactor(decay);

            if (decay < 0.1 && entity.getState() == MemoryState.ACTIVE) {
                entity.setState(MemoryState.DECAYED);
                log.info("[MemoryDecayManager] Memory '{}' state updated to DECAYED (decay={})", entity.getId(), decay);
            }
        }
        memoryRepository.saveAll(memories);
    }

    @Override
    public double computeDecayFactor(MemoryEntity entity) {
        if (entity == null || entity.getLastAccessedAt() == null) return 1.0;
        long daysUnused = Duration.between(entity.getLastAccessedAt(), Instant.now()).toDays();
        if (daysUnused <= 0) return 1.0;

        // Ebbinghaus Forgetting Curve formula: R = e^(-t / S)
        double stability = Math.max(1.0, entity.getImportanceScore() * 10.0 + Math.log1p(entity.getAccessCount()));
        return Math.exp(-defaultDecayRate * daysUnused / stability);
    }
}
