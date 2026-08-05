package com.atlas.keywordsearch.security;

import com.atlas.domain.security.UserRole;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class JwtTokenProviderTest {

    private JwtTokenProvider jwtTokenProvider;

    @BeforeEach
    void setUp() {
        jwtTokenProvider = new JwtTokenProvider();
    }

    @Test
    void testTokenGenerationAndValidation() {
        String token = jwtTokenProvider.generateToken("admin", UserRole.ADMIN);
        assertNotNull(token);
        assertTrue(jwtTokenProvider.validateToken(token));
    }
}
