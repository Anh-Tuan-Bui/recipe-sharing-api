package com.example.recipe_sharing.service;

import com.example.recipe_sharing.dto.request.LoginRequest;
import com.example.recipe_sharing.dto.request.SignupRequest;
import com.example.recipe_sharing.dto.response.AuthResponse;
import com.example.recipe_sharing.dto.response.UserResponse;

public interface AuthService {
    AuthResponse login(LoginRequest request);
    UserResponse signup(SignupRequest request);
}
