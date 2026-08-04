package com.atlas.common.utils;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public final class HashUtils {

    private HashUtils() {}

    public static String sha256(String input) {
        if (input == null) return "";
        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            byte[] hash = digest.digest(input.getBytes(StandardCharsets.UTF_8));
            StringBuilder hexString = new StringBuilder();
            for (byte b : hash) {
                String hex = Integer.toHexString(0xff & b);
                if (hex.length() == 1) hexString.append('0');
                hexString.append(hex);
            }
            return hexString.toString();
        } catch (NoSuchAlgorithmException e) {
            throw new IllegalStateException("SHA-256 algorithm unavailable", e);
        }
    }

    public static long calculateSimHash64(String content) {
        if (StringUtils.isNullOrBlank(content)) return 0L;
        int[] v = new int[64];
        String[] tokens = content.toLowerCase().split("\\s+");
        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            for (String token : tokens) {
                byte[] hashBytes = digest.digest(token.getBytes(StandardCharsets.UTF_8));
                long hash = 0L;
                for (int b = 0; b < 8; b++) {
                    hash = (hash << 8) | (hashBytes[b] & 0xFF);
                }
                for (int i = 0; i < 64; i++) {
                    if (((hash >> i) & 1L) == 1L) {
                        v[i] += 1;
                    } else {
                        v[i] -= 1;
                    }
                }
            }
        } catch (NoSuchAlgorithmException e) {
            throw new IllegalStateException("SHA-256 algorithm unavailable", e);
        }

        long fingerprint = 0L;
        for (int i = 0; i < 64; i++) {
            if (v[i] > 0) {
                fingerprint |= (1L << i);
            }
        }
        return fingerprint;
    }

    public static int hammingDistance(long h1, long h2) {
        return Long.bitCount(h1 ^ h2);
    }
}
