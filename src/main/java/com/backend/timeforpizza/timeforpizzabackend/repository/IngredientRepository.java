package com.backend.timeforpizza.timeforpizzabackend.repository;

import com.backend.timeforpizza.timeforpizzabackend.model.Ingredient;
import com.backend.timeforpizza.timeforpizzabackend.model.Recipe;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository("ingredientRepository")
public interface IngredientRepository extends CrudRepository<Ingredient, Integer> {
    Iterable<Ingredient> findByRecipe(Recipe recipe);
    void deleteByRecipe(Recipe recipe);
}
