package com.atlas.parserservice.parser;

import com.atlas.common.utils.HashUtils;
import org.springframework.stereotype.Component;

@Component
public class SimHashDetector {

    public long calculateSimHash(String text) {
        if (text == null || text.isBlank()) return 0L;
        return HashUtils.calculateSimHash64(text);
    }

    public int calculateHammingDistance(long hash1, long hash2) {
        return HashUtils.hammingDistance(hash1, hash2);
    }

    public boolean isNearDuplicate(long hash1, long hash2, int threshold) {
        if (hash1 == 0L || hash2 == 0L) return false;
        return calculateHammingDistance(hash1, hash2) <= threshold;
    }
}
