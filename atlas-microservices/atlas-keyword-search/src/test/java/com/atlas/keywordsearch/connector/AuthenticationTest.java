package com.atlas.keywordsearch.connector;

import com.atlas.domain.connector.ConnectorAuthentication;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class AuthenticationTest {

    private CredentialEncryptor encryptor;
    private SecretRotationService rotationService;

    @BeforeEach
    void setUp() {
        encryptor = new CredentialEncryptor();
        rotationService = new SecretRotationService(encryptor);
    }

    @Test
    void testEncryptAndDecrypt() {
        String raw = "my-secret-pat-token-12345";
        String encrypted = encryptor.encrypt(raw);
        assertNotNull(encrypted);
        assertTrue(encrypted.startsWith("ENC("));

        String decrypted = encryptor.decrypt(encrypted);
        assertEquals(raw, decrypted);
    }

    @Test
    void testRotateSecrets() {
        ConnectorAuthentication auth = rotationService.rotateSecrets("github", "new-api-token-999");
        assertNotNull(auth);
        assertNotNull(auth.getEncryptedAccessToken());
    }
}
