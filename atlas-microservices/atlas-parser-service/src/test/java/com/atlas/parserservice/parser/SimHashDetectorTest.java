package com.atlas.parserservice.parser;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class SimHashDetectorTest {

    private SimHashDetector detector;

    @BeforeEach
    void setUp() {
        detector = new SimHashDetector();
    }

    @Test
    void testSimHashCalculationAndHammingDistance() {
        String text1 = "Atlas is a distributed cloud-native AI search engine platform built for large scale document retrieval.";
        String text2 = "Atlas is a distributed cloud-native AI search engine platform constructed for large scale document retrieval.";

        long hash1 = detector.calculateSimHash(text1);
        long hash2 = detector.calculateSimHash(text2);
        long hashSame = detector.calculateSimHash(text1);

        assertNotEquals(0L, hash1);
        assertNotEquals(0L, hash2);

        assertEquals(0, detector.calculateHammingDistance(hash1, hashSame));

        int distance = detector.calculateHammingDistance(hash1, hash2);
        assertTrue(distance <= 15, "Hamming distance for near duplicate documents should be low");
        assertTrue(detector.isNearDuplicate(hash1, hash2, 15));
    }
}
