package com.backend.timeforpizza.timeforpizzabackend.service;

import com.backend.timeforpizza.timeforpizzabackend.exception.ResourceNotFoundException;
import com.backend.timeforpizza.timeforpizzabackend.model.Ingredient;
import com.backend.timeforpizza.timeforpizzabackend.repository.IngredientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class IngredientService {

    private final IngredientRepository ingredientRepository;

    @Autowired
    public IngredientService(IngredientRepository ingredientRepository) {
        this.ingredientRepository = ingredientRepository;
    }

    public Ingredient addIngredient(Ingredient ingredient) {
        if (ingredient.getRecipe() == null) {
            // TODO
        }
        return ingredientRepository.save(ingredient);
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
    }

    public void deleteAllIngredientsByRecipeId(Long recipeId) {
        ingredientRepository.deleteAllByRecipeRecipeId(recipeId);
    }

    public Ingredient updateIngredient(Long ingredientId, Ingredient newIngredient) {
        Ingredient oldIngredient = ingredientRepository.findById(ingredientId)
                .orElseThrow(() -> new ResourceNotFoundException("Ingredient", "ingredientId", ingredientId));

        oldIngredient.setAmount(newIngredient.getAmount());
        oldIngredient.setName(newIngredient.getName());
        oldIngredient.setUnit(newIngredient.getUnit());
        return ingredientRepository.save(oldIngredient);
    }

    public List<Ingredient> updateAllIngredientsByRecipeId(Long recipeId, List<Ingredient> ingredients) {
        ingredientRepository.deleteAllByRecipeRecipeId(recipeId);

        return ingredientRepository.saveAll(ingredients);
    }

}
