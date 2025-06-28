package com.example.recipe_sharing.service.impl;

import com.example.recipe_sharing.dto.request.LoginRequest;
import com.example.recipe_sharing.dto.request.SignupRequest;
import com.example.recipe_sharing.dto.response.AuthResponse;
import com.example.recipe_sharing.dto.response.UserResponse;
import com.example.recipe_sharing.entity.UserEntity;
import com.example.recipe_sharing.enums.Role;
import com.example.recipe_sharing.exception.ExistedException;
import com.example.recipe_sharing.repository.UserRepository;
import com.example.recipe_sharing.service.AuthService;
import com.example.recipe_sharing.service.JwtService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.modelmapper.ModelMapper;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class AuthServiceImpl implements AuthService {
    AuthenticationManager authenticationManager;
    JwtService jwtService;
    ModelMapper modelMapper;
    UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public AuthResponse login(LoginRequest request) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(request.getUsername(), request.getPassword())
        );

        UserEntity userEntity = userRepository.findByUsername(request.getUsername())
                .orElseThrow(() -> new RuntimeException("User not found"));
        String token = jwtService.generateToken(userEntity);

        return AuthResponse.builder()
                .token(token)
                .user(modelMapper.map(userEntity, UserResponse.class))
                .build();
    }

    @Override
    public UserResponse signup(SignupRequest request) {
        userRepository.findByUsername(request.getUsername())
                .ifPresent(user -> {
                    throw new ExistedException("Username is already to use");
                });

        UserEntity savedUser = userRepository.save(UserEntity.builder()
                .username(request.getUsername())
                .password(passwordEncoder.encode(request.getPassword()))
                .name(request.getName())
                .email(request.getEmail())
                .role(request.getRole())
                .createdAt(LocalDateTime.now())
                .lastPasswordChangeAt(LocalDateTime.now())
                .build()
        );

        return modelMapper.map(savedUser, UserResponse.class);
    }
}
