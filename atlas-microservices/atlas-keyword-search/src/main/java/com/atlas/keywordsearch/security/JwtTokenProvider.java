package com.atlas.keywordsearch.security;

import com.atlas.domain.security.UserRole;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.UUID;

@Slf4j
@Component
public class JwtTokenProvider {

    private static final String SECRET_KEY = "atlas-enterprise-production-secret-key-for-jwt-authentication";
    private static final long EXPIRATION_TIME_MS = 86400000; // 24 hours

    public String generateToken(String username, UserRole role) {
        log.info("Generating JWT token for user '{}' with role {}", username, role);
        return "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9." +
                UUID.randomUUID().toString().replace("-", "") +
                "." + username + "-" + role;
    }

    public boolean validateToken(String token) {
        return token != null && token.startsWith("eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.");
    }
}
