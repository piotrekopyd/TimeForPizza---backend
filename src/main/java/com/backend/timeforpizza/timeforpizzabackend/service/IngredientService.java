package com.backend.timeforpizza.timeforpizzabackend.service;

import com.backend.timeforpizza.timeforpizzabackend.exception.ResourceNotFoundException;
import com.backend.timeforpizza.timeforpizzabackend.model.Ingredient;
import com.backend.timeforpizza.timeforpizzabackend.repository.IngredientRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class IngredientService {

    private final IngredientRepository ingredientRepository;

    private static final Logger logger = LoggerFactory.getLogger(IngredientService.class);

    @Autowired
    public IngredientService(IngredientRepository ingredientRepository) {
        this.ingredientRepository = ingredientRepository;
    }

    public Ingredient addIngredient(Ingredient ingredient) {
        if (ingredient.getRecipe() == null) {
            // TODO
        }
        ingredient = ingredientRepository.save(ingredient);
        logger.info("Added ingredient with id: {}.", ingredient.getIngredientId());
        return ingredient;
    }

    public List<Ingredient> addAllIngredients(List<Ingredient> ingredients) {
        for (Ingredient ingredient: ingredients) {
            if (ingredient.getRecipe() == null) {
                // TODo
            }
        }
        return ingredientRepository.saveAll(ingredients);
    }

    public List<Ingredient> getAllIngredientsByRecipeId(Long recipeId) {
        return ingredientRepository.findAllByRecipeRecipeId(recipeId);
    }

    public void deleteIngredientById(Long ingredientId) {
        ingredientRepository.deleteById(ingredientId);
        logger.info("Deleted ingredient with id: {}.", ingredientId);
    }

    public void deleteAllIngredientsByRecipeId(Long recipeId) {
        ingredientRepository.deleteAllByRecipeRecipeId(recipeId);
        logger.info("Deleted all ingredients for recipe with id: {}.", recipeId);
    }

    public Ingredient updateIngredientById(Long ingredientId, Ingredient newIngredient) {
        Ingredient oldIngredient = ingredientRepository.findById(ingredientId)
                .orElseThrow(() -> new ResourceNotFoundException("Ingredient", "ingredientId", ingredientId));

        oldIngredient.setAmount(newIngredient.getAmount());
        oldIngredient.setName(newIngredient.getName());
        oldIngredient.setUnit(newIngredient.getUnit());
        oldIngredient = ingredientRepository.save(oldIngredient);
        logger.info("Updated ingredient with id: {}.", ingredientId);
        return ingredientRepository.save(oldIngredient);
    }

    public List<Ingredient> updateAllIngredientsByRecipeId(Long recipeId, List<Ingredient> ingredients) {
        ingredientRepository.deleteAllByRecipeRecipeId(recipeId);
        ingredients = ingredientRepository.saveAll(ingredients);
        logger.info("Updated ingredients for recipe with id: {}.", recipeId);
        return ingredients;
    }

}
