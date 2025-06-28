package com.example.recipe_sharing.controller;

import com.example.recipe_sharing.dto.request.RecipeRequest;
import com.example.recipe_sharing.dto.response.RecipeResponse;
import com.example.recipe_sharing.service.RecipeService;
import com.fasterxml.jackson.core.JsonProcessingException;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PagedModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.User;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;


@RestController
@RequiredArgsConstructor
@RequestMapping("api/recipes")
public class RecipeController {

    private final RecipeService recipeService;

    @GetMapping
    public ResponseEntity<PagedModel<RecipeResponse>> getAllRecipes(
            @RequestParam(defaultValue = "") String keyword,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size
    ) {
        Pageable pageable = PageRequest.of(page, size);

        return ResponseEntity.ok(recipeService.getAllRecipes(keyword, pageable));
    }

    @GetMapping("/{id}")
    public RecipeResponse getRecipeById(@PathVariable long id) {
        return recipeService.getRecipeById(id);
    }

    @PreAuthorize("hasRole('CHEF')")
    @PostMapping
    public ResponseEntity<RecipeResponse> createRecipe(@RequestBody @Valid RecipeRequest recipeRequest,
                                                       @AuthenticationPrincipal User currentUser) throws JsonProcessingException {
        RecipeResponse response = recipeService.createRecipe(recipeRequest, currentUser);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(response);
    }

    @PreAuthorize("hasRole('CHEF')")
    @PutMapping("/update/{id}")
    public ResponseEntity<RecipeResponse> updateRecipe(@PathVariable long id,
                                                       @RequestBody @Valid RecipeRequest request,
                                                       @RequestParam(name = "new_images", required = false) List<MultipartFile> newImages,
                                                       @AuthenticationPrincipal User currentUser) throws JsonProcessingException {
        RecipeResponse response = recipeService.updateRecipe(id, request, newImages, currentUser);

        return ResponseEntity.ok(response);
    }

    @PreAuthorize("hasRole('CHEF')")
    @PatchMapping("/delete_image/{id}")
    public ResponseEntity<RecipeResponse> deleteImageFromRecipe(@PathVariable long id,
                                                                @RequestParam String imageUrl,
                                                                @AuthenticationPrincipal User currentUser) throws JsonProcessingException {
        RecipeResponse response = recipeService.deleteImageFromRecipe(id, imageUrl, currentUser);

        return ResponseEntity.ok(response);
    }

    @PreAuthorize("hasRole('CHEF')")
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> deleteRecipe(@PathVariable long id, @AuthenticationPrincipal User currentUser) {
        recipeService.deleteRecipe(id, currentUser);

        return ResponseEntity.noContent().build();
    }
}
