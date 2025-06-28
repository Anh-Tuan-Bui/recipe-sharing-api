package com.example.recipe_sharing.service;

import com.example.recipe_sharing.dto.request.UserRequest;
import com.example.recipe_sharing.dto.response.UserResponse;

public interface UserService {
    UserResponse updateUser(UserRequest request, Long userId);
}
