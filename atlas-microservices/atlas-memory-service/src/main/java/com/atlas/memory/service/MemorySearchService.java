package com.atlas.memory.service;

import com.atlas.common.dto.memory.MemorySearchRequest;
import com.atlas.domain.memory.MemorySearchResult;

import java.util.List;

public interface MemorySearchService {
    List<MemorySearchResult> searchMemories(String tenantId, MemorySearchRequest request);
}
