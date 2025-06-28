package com.example.recipe_sharing.service.impl;

import com.example.recipe_sharing.entity.UserEntity;
import com.example.recipe_sharing.exception.NotFoundException;
import com.example.recipe_sharing.repository.UserRepository;
import com.example.recipe_sharing.service.JwtService;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.time.ZoneId;
import java.util.Date;

@Service
@RequiredArgsConstructor
public class JwtServiceImpl implements JwtService {
    private final UserRepository userRepository;
    private static final String SECRET_KEY = "12345678901234567890123456789012";

    private SecretKey getSignKey() {
        return Keys.hmacShaKeyFor(SECRET_KEY.getBytes());
    }

    @Override
    public String generateToken(UserEntity user) {
        return Jwts.builder()
                .subject(user.getUsername())
                .issuedAt(new Date())
                .expiration(new Date(System.currentTimeMillis() + 86400000))
                .claim("userId", user.getId())
                .signWith(getSignKey())
                .compact();
    }

    @Override
    public Claims extractAllClaimsFromToken(String token) {
        return Jwts.parser()
                .verifyWith(getSignKey())
                .build()
                .parseSignedClaims(token)
                .getPayload();
    }

    @Override
    public String extractUsername(String token) {
        return extractAllClaimsFromToken(token).getSubject();
    }

    @Override
    public Date extractIssuedAt(String token) {
        return extractAllClaimsFromToken(token).getIssuedAt();
    }

    @Override
    public Long extractUserId(String token) {
        return extractAllClaimsFromToken(token).get("userId", Long.class);
    }

    @Override
    public boolean isTokenValid(String token, Long userId) {
        UserEntity user = userRepository.findById(userId)
                .orElseThrow(() -> new NotFoundException("User Not Found"));

        return user.getUsername().equals(extractUsername(token))
                && !isTokenExpired(token)
                && user.getLastPasswordChangeAt().isBefore(
                        extractIssuedAt(token)
                                .toInstant().atZone(ZoneId.systemDefault())
                                .toLocalDateTime()
                );
    }

    @Override
    public boolean isTokenExpired(String token) {
        return extractAllClaimsFromToken(token).getExpiration().before(new Date());
    }
}