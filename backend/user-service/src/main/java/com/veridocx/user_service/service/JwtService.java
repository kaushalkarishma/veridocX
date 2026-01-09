package com.veridocx.user_service.service;

import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.util.Base64;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Service
public class JwtService {

    @Value("${jwt.secret:}")
    private String secret;            // instance field, injected properly

    private SecretKey getKey() {
        if (secret == null || secret.length() < 32) {
            // auto-generate a valid 256-bit key (dev only)
            SecretKey key = Keys.secretKeyFor(SignatureAlgorithm.HS256);
            System.out.println("⚠ GENERATED TEMP JWT KEY: " +
                    Base64.getEncoder().encodeToString(key.getEncoded()));
            return key;
        }
        return Keys.hmacShaKeyFor(secret.getBytes());
    }

    private final long expirationMs = 24L * 60 * 60 * 1000; // 24 hours

    // validate token and return claims (instance method)
    public Claims validateToken(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(getKey())
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

    // generate token (instance method)
    public String generateToken(String userId, String email, String role) {
        Map<String, Object> claims = new HashMap<>();
        claims.put("email", email);
        claims.put("role", role);

        return Jwts.builder()
                .setClaims(claims)
                .setSubject(userId)
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + expirationMs))
                .signWith(getKey(), SignatureAlgorithm.HS256)
                .compact();
}
}
