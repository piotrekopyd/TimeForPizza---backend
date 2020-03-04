package com.backend.timeforpizza.timeforpizzabackend.repository;

import com.backend.timeforpizza.timeforpizzabackend.model.ImageUrl;
import com.backend.timeforpizza.timeforpizzabackend.model.Recipe;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository("imagesRepository")
public interface ImagesRepository extends JpaRepository<ImageUrl, Long> {
    Optional<ImageUrl> findByUrlAndRecipe(String url, Recipe recipe);
    Boolean existsByUrlAndRecipe(String url, Recipe recipe);
}