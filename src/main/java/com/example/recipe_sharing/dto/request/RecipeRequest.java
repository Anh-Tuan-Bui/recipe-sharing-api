package com.example.recipe_sharing.dto.request;

import jakarta.validation.constraints.NotBlank;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.experimental.FieldDefaults;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Getter
@FieldDefaults(level = AccessLevel.PRIVATE)
public class RecipeRequest {

    @NotBlank(message = "Title is required")
    String title;

    String description;

    @NotBlank(message = "Image is required")
    List<MultipartFile> images;

    @NotBlank(message = "Ingredient is required")
    String ingredients;

    @NotBlank(message = "Instruction is required")
    String instructions;

    String labels;
}
