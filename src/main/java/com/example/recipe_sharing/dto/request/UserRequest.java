package com.example.recipe_sharing.dto.request;

import com.example.recipe_sharing.enums.Role;
import jakarta.validation.constraints.Email;
import lombok.Getter;

@Getter
public class UserRequest {
    private String password;

    @Email(message = "Email invalid format")
    private String email;
    private String name;
    private Role role;
}
