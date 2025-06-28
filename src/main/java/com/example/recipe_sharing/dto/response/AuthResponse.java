package com.example.recipe_sharing.dto.response;

import lombok.*;

@Setter
@Getter
@Builder
public class AuthResponse {
    String token;
    UserResponse user;
}
