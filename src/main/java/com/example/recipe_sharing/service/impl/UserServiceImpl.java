package com.example.recipe_sharing.service.impl;

import com.example.recipe_sharing.dto.request.UserRequest;
import com.example.recipe_sharing.dto.response.UserResponse;
import com.example.recipe_sharing.entity.UserEntity;
import com.example.recipe_sharing.exception.NotFoundException;
import com.example.recipe_sharing.repository.UserRepository;
import com.example.recipe_sharing.service.UserService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final ModelMapper modelMapper;
    private final PasswordEncoder passwordEncoder;

    @Override
    public UserResponse updateUser(UserRequest request, Long userId) {
        UserEntity user = userRepository.findById(userId)
                .orElseThrow(() -> new NotFoundException("User not found"));

        if (request.getPassword() != null && !user.getPassword().isBlank()) {
            user.setPassword(passwordEncoder.encode(request.getPassword()));
            user.setLastPasswordChangeAt(LocalDateTime.now());
        }
        if (request.getEmail() != null && !request.getEmail().isBlank()) {
            user.setEmail(request.getEmail());
        }
        if (request.getName() != null) {
            user.setName(request.getName());
        }
        if (request.getRole() != null) {
            if (user.getRole().name().equals("ADMIN") || user.getRole().name().equals("USER")) {
                user.setRole(request.getRole());
            }
        }

        return modelMapper.map(userRepository.save(user), UserResponse.class);
    }
}
