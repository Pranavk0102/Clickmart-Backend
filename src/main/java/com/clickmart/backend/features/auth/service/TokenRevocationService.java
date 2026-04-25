package com.clickmart.backend.features.auth.service;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.HexFormat;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.clickmart.backend.config.JwtUtil;
import com.clickmart.backend.entity.RevokedToken;
import com.clickmart.backend.features.auth.repository.RevokedTokenRepository;

@Service
@Transactional
public class TokenRevocationService {

    private final RevokedTokenRepository revokedTokenRepository;

    private final JwtUtil jwtUtil;

    @Autowired
    public TokenRevocationService(RevokedTokenRepository revokedTokenRepository,
                               JwtUtil jwtUtil) {
        this.revokedTokenRepository = revokedTokenRepository;
        this.jwtUtil = jwtUtil;
    }

    @Transactional(readOnly = true)
    public boolean isRevoked(String token) {
        if (!hasText(token)) {
            return false;
        }
        return revokedTokenRepository.existsByTokenHashAndExpiresAtAfter(hashToken(token), LocalDateTime.now());
    }

    public void revokeToken(String token) {
        if (!hasText(token)) {
            return;
        }

        try {
            revokedTokenRepository.deleteByExpiresAtBefore(LocalDateTime.now());

            String tokenHash = hashToken(token);
            if (revokedTokenRepository.existsByTokenHash(tokenHash)) {
                return;
            }

            RevokedToken revokedToken = new RevokedToken();
            revokedToken.setTokenHash(tokenHash);
            revokedToken.setUsername(jwtUtil.extractUsername(token));
            String tokenType = jwtUtil.extractTokenType(token);
            revokedToken.setTokenType(hasText(tokenType) ? tokenType : "unknown");
            revokedToken.setExpiresAt(LocalDateTime.ofInstant(
                    jwtUtil.extractExpiration(token).toInstant(),
                    ZoneId.systemDefault()));
            revokedTokenRepository.save(revokedToken);
        } catch (Exception ignored) {
        }
    }

    private String hashToken(String token) {
        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            byte[] hashed = digest.digest(token.getBytes(StandardCharsets.UTF_8));
            return HexFormat.of().formatHex(hashed);
        } catch (NoSuchAlgorithmException ex) {
            throw new IllegalStateException("SHA-256 algorithm is not available", ex);
        }
    }

    private boolean hasText(String value) {
        return value != null && !value.isBlank();
    }
}
