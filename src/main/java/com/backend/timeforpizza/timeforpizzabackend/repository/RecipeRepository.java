package com.backend.timeforpizza.timeforpizzabackend.repository;

import com.backend.timeforpizza.timeforpizzabackend.model.Recipe;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository("recipeRepository")
public interface RecipeRepository extends CrudRepository<Recipe, Integer> {
}
