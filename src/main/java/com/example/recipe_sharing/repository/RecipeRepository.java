package com.example.recipe_sharing.repository;

import com.example.recipe_sharing.entity.RecipeEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RecipeRepository extends JpaRepository<RecipeEntity, Long> {
    Page<RecipeEntity> findByTitleContainingIgnoreCase(String keyword, Pageable pageable);
}
