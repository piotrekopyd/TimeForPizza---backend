package com.backend.timeforpizza.timeforpizzabackend.repository;

import com.backend.timeforpizza.timeforpizzabackend.model.RecipeImage;
import com.backend.timeforpizza.timeforpizzabackend.model.Recipe;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RecipeImageRepository extends JpaRepository<RecipeImage, Long> {
    Optional<RecipeImage> findByUrlAndRecipeRecipeId(String url, Long recipeId);
    Boolean existsByUrlAndRecipeRecipeId(String url, Long recipeId);
    void deleteAllByRecipeRecipeId(Long recipeId);
}