package com.example.recipe_sharing.dto.request;

import com.example.recipe_sharing.enums.Role;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.experimental.FieldDefaults;

@Getter
@FieldDefaults(level = AccessLevel.PACKAGE)
public class SignupRequest {
    @NotBlank(message = "Username is required")
    String username;

    @NotBlank(message = "Password is required")
    String password;

    String name = "";

    @NotBlank(message = "Email is required")
    @Email(message = "Invalid email format")
    String email;

    @Enumerated(EnumType.STRING)
    Role role = Role.USER;
}
