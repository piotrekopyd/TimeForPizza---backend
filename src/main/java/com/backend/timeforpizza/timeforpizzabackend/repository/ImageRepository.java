package com.backend.timeforpizza.timeforpizzabackend.repository;

import com.backend.timeforpizza.timeforpizzabackend.model.Image;
import com.backend.timeforpizza.timeforpizzabackend.model.Recipe;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ImageRepository extends JpaRepository<Image, Long> {
    Optional<Image> findByUrlAndRecipe(String url, Recipe recipe);
    Boolean existsByUrlAndRecipe(String url, Recipe recipe);
    void deleteAllByRecipeRecipeId(Long recipeId);
}