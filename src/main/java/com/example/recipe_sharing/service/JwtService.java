package com.example.recipe_sharing.service;

import com.example.recipe_sharing.entity.UserEntity;
import io.jsonwebtoken.Claims;

import java.util.Date;

public interface JwtService {
    String generateToken(UserEntity user);
    Claims extractAllClaimsFromToken(String token);
    String extractUsername(String token);
    Date extractIssuedAt(String token);
    Long extractUserId(String token);
    boolean isTokenValid(String token, Long userId);
    boolean isTokenExpired(String token);
}
