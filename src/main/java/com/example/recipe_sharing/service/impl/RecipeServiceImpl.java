package com.example.recipe_sharing.service.impl;

import com.example.recipe_sharing.dto.request.RecipeRequest;
import com.example.recipe_sharing.dto.response.RecipeResponse;
import com.example.recipe_sharing.entity.RecipeEntity;
import com.example.recipe_sharing.entity.UserEntity;
import com.example.recipe_sharing.exception.NotFoundException;
import com.example.recipe_sharing.repository.RecipeRepository;
import com.example.recipe_sharing.repository.UserRepository;
import com.example.recipe_sharing.service.FileStorageService;
import com.example.recipe_sharing.service.RecipeService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PagedModel;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class RecipeServiceImpl implements RecipeService {

    private final RecipeRepository recipeRepository;
    private final UserRepository userRepository;
    private final FileStorageService fileStorageService;
    private final ModelMapper mapper;
    private final ObjectMapper objectMapper;

    @Override
    public RecipeResponse createRecipe(RecipeRequest request, User currentUser) throws JsonProcessingException {
        UserEntity userEntity = userRepository.findByUsername(currentUser.getUsername())
                .orElseThrow(() -> new UsernameNotFoundException("Username not found"));

        List<String> imageUrls = fileStorageService.uploadMultipartFile(request.getImages()).get("success");
        String jsonUrls = objectMapper.writeValueAsString(imageUrls);

        RecipeEntity recipe = RecipeEntity.builder()
                .imageUrlsJson(jsonUrls)
                .createdBy(userEntity)
                .publishedAt(LocalDateTime.now())
                .build();
        mapper.map(request, recipe);

        return mapper.map(recipeRepository.save(recipe), RecipeResponse.class);
    }

    @Override
    public RecipeResponse updateRecipe(Long id,
                                       RecipeRequest recipeRequest,
                                       List<MultipartFile> newImages,
                                       User currentUser) throws JsonProcessingException {
        RecipeEntity recipe = recipeRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Recipe not found"));

        if (!recipe.getCreatedBy().getUsername().equals(currentUser.getUsername())) {
            throw new AccessDeniedException("You don't have permission to update this recipe");
        }

        mapper.map(recipeRequest, recipe);
        if (!newImages.isEmpty()) {
            List<String> oldUrls = objectMapper.readValue(recipe.getImageUrlsJson(), new TypeReference<>() {});
            List<String> newUrls = fileStorageService.uploadMultipartFile(newImages).get("success");

            oldUrls.addAll(newUrls);
            String jsonUrls = objectMapper.writeValueAsString(oldUrls);

            recipe.setImageUrlsJson(jsonUrls);
        }

        return mapper.map(recipeRepository.save(recipe), RecipeResponse.class);
    }

    @Override
    public RecipeResponse deleteImageFromRecipe(Long id, String imageUrl, User currentUser) throws JsonProcessingException {
        RecipeEntity recipe = recipeRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Recipe not found"));
        if (!recipe.getCreatedBy().getUsername().equals(currentUser.getUsername())) {
            throw new AccessDeniedException("You don't have permission to delete this image");
        }

        List<String> imageUrls = objectMapper.readValue(recipe.getImageUrlsJson(), new TypeReference<>() {});
        if (!imageUrls.contains(imageUrl)) {
            throw new NotFoundException("Image not found in this recipe");
        }

        String publicId = extractPublicIdFromUrl(imageUrl);
        fileStorageService.deleteFile(publicId);
        imageUrls.remove(imageUrl);
        String jsonUrls = objectMapper.writeValueAsString(imageUrls);
        recipe.setImageUrlsJson(jsonUrls);

        return mapper.map(recipeRepository.save(recipe), RecipeResponse.class);
    }

    private String extractPublicIdFromUrl(String imageUrl) {
        //Example: imageUrl = https://res.cloudinary.com/env-dev/image/upload/v1750691034/recipe_sharing/ff56028c-5aeb-4de2-97e5-cf5905eb4bca.jpg
        String[] parts = imageUrl.split("/upload/");
        if (parts.length < 2) {
            throw new IllegalArgumentException("Invalid image url");
        }

        String path = parts[1]; //path = v1750...
        int slashIndex = path.indexOf("/");
        if (slashIndex == -1) {
            path = path.substring(slashIndex + 1); // path = recipe_sharing/ff56...
        }
        int dotIndex = path.lastIndexOf(".");
        if (dotIndex != -1) {
            path = path.substring(0, dotIndex);
        }

        return path;
    }

    @Override
    public void deleteRecipe(Long id, User currentUser) {
        RecipeEntity recipe = recipeRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Recipe not found"));
        if (!recipe.getCreatedBy().getUsername().equals(currentUser.getUsername())) {
            throw new AccessDeniedException("You don't have permission to delete this recipe");
        }

        recipeRepository.delete(recipe);
    }

    @Override
    public PagedModel<RecipeResponse> getAllRecipes(String keyword, Pageable pageable) {
        Page<RecipeEntity> page = recipeRepository.findByTitleContainingIgnoreCase(keyword, pageable);
        Page<RecipeResponse> responses = page.map(recipe -> mapper.map(recipe, RecipeResponse.class));

        return new PagedModel<>(responses);
    }

    @Override
    public RecipeResponse getRecipeById(Long id) {
        RecipeEntity recipe = recipeRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Recipe not found"));

        return mapper.map(recipe, RecipeResponse.class);
    }
}
