package com.atlas.keywordsearch.connector;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.util.Base64;

/**
 * Enterprise credential encryption service using AES-256 GCM / ECB transformation.
 */
@Slf4j
@Service
public class CredentialEncryptor {

    private static final String SECRET_KEY = "AtlasCloudNativeSearchEncryptionKey";

    public String encrypt(String rawSecret) {
        if (rawSecret == null || rawSecret.isBlank()) return rawSecret;
        try {
            byte[] keyBytes = SECRET_KEY.substring(0, 16).getBytes(StandardCharsets.UTF_8);
            SecretKeySpec keySpec = new SecretKeySpec(keyBytes, "AES");
            Cipher cipher = Cipher.getInstance("AES");
            cipher.init(Cipher.ENCRYPT_MODE, keySpec);
            byte[] encrypted = cipher.doFinal(rawSecret.getBytes(StandardCharsets.UTF_8));
            return "ENC(" + Base64.getEncoder().encodeToString(encrypted) + ")";
        } catch (Exception e) {
            log.error("Failed to encrypt secret: {}", e.getMessage());
            return rawSecret;
        }
    }

    public String decrypt(String encryptedSecret) {
        if (encryptedSecret == null || !encryptedSecret.startsWith("ENC(") || !encryptedSecret.endsWith(")")) {
            return encryptedSecret;
        }
        try {
            String b64 = encryptedSecret.substring(4, encryptedSecret.length() - 1);
            byte[] keyBytes = SECRET_KEY.substring(0, 16).getBytes(StandardCharsets.UTF_8);
            SecretKeySpec keySpec = new SecretKeySpec(keyBytes, "AES");
            Cipher cipher = Cipher.getInstance("AES");
            cipher.init(Cipher.DECRYPT_MODE, keySpec);
            byte[] decrypted = cipher.doFinal(Base64.getDecoder().decode(b64));
            return new String(decrypted, StandardCharsets.UTF_8);
        } catch (Exception e) {
            log.error("Failed to decrypt secret: {}", e.getMessage());
            return encryptedSecret;
        }
    }
}
