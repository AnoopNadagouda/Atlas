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
        for (String token : tokens) {
            long hash = sha256(token).hashCode();
            for (int i = 0; i < 64; i++) {
                if (((hash >> i) & 1) == 1) {
                    v[i] += 1;
                } else {
                    v[i] -= 1;
                }
            }
        }
        long fingerprint = 0L;
        for (int i = 0; i < 64; i++) {
            if (v[i] > 0) {
                fingerprint |= (1L << i);
            }
        }
        return fingerprint;
    }
}
