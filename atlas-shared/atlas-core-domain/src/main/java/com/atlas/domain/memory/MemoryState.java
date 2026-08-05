package com.atlas.domain.memory;

/**
 * State lifecycle of a memory entry.
 */
public enum MemoryState {
    ACTIVE,
    CONSOLIDATED,
    DECAYED,
    ARCHIVED,
    EXPIRED
}
