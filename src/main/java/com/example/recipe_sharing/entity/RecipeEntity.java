package com.example.recipe_sharing.entity;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "recipe")
@FieldDefaults(level = AccessLevel.PRIVATE)
public class RecipeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    String title;
    String description;
    String imageUrlsJson;
    String ingredients;
    String instructions;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    UserEntity createdBy;

    @Temporal(TemporalType.TIMESTAMP)
    LocalDateTime publishedAt;

    String labels;
}
