package com.example.recipe_sharing.dto.response;

import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.LocalDateTime;
import java.util.List;

@Setter
@Getter
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class RecipeResponse {
    Long id;
    String title;
    String description;
    List<String> imageUrls;
    String labels;
    String chefName;
    LocalDateTime publishedAt;
}
