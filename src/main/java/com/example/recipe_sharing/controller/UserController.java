package com.example.recipe_sharing.controller;

import com.example.recipe_sharing.dto.request.UserRequest;
import com.example.recipe_sharing.dto.response.UserResponse;
import com.example.recipe_sharing.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/users")
public class UserController {
    private final UserService userService;

    @PatchMapping("/{id}")
    public ResponseEntity<UserResponse> updateUser(@RequestBody @Valid UserRequest request,
                                                   @PathVariable Long id) {
        return ResponseEntity.ok(userService.updateUser(request, id));
    }
}
