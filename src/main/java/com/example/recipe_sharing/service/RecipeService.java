package com.example.recipe_sharing.service;

import com.example.recipe_sharing.dto.request.RecipeRequest;
import com.example.recipe_sharing.dto.response.RecipeResponse;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PagedModel;
import org.springframework.security.core.userdetails.User;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface RecipeService {
    RecipeResponse createRecipe(RecipeRequest recipeRequest, User currentUser) throws JsonProcessingException;
    RecipeResponse updateRecipe(Long id, RecipeRequest recipeRequest, List<MultipartFile> newImages, User currentUser) throws JsonProcessingException;
    RecipeResponse deleteImageFromRecipe(Long id, String imageUrl, User currentUser) throws JsonProcessingException;
    void deleteRecipe(Long id, User currentUser);
    PagedModel<RecipeResponse> getAllRecipes(String keyword, Pageable pageable);
    RecipeResponse getRecipeById(Long id);
}
