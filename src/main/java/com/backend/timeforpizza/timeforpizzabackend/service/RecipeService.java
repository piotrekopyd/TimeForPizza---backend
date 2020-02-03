package com.backend.timeforpizza.timeforpizzabackend.service;

import com.backend.timeforpizza.timeforpizzabackend.model.Recipe;
import com.backend.timeforpizza.timeforpizzabackend.repository.RecipeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class RecipeService {
    private final RecipeRepository recipeRepository;

    @Autowired
    public RecipeService(@Qualifier("recipeRepository") RecipeRepository recipeRepository) {
        this.recipeRepository = recipeRepository;
    }

    public int addRecipe(Recipe recipe) {
        return recipeRepository.save(recipe) != null ? 1 : -1;
    }

    public Recipe getRecipeById(int id) {
        return recipeRepository.findById(id)
                .orElse(null);
    }

    public List<Recipe> getAllRecipes() {
        List<Recipe> recipes = new ArrayList<>();
        recipeRepository.findAll()
                .forEach(recipes::add);
        return recipes;
    }

    public int updateRecipe(Recipe newRecipe) {
        Recipe oldRecipe = recipeRepository.findById(newRecipe.getId())
                .orElse(null);
        if(oldRecipe != null) {
            oldRecipe.setComments(newRecipe.getComments());
            oldRecipe.setImageUrl(newRecipe.getImageUrl());
            oldRecipe.setIngredients(newRecipe.getIngredients());
            oldRecipe.setPreparation(newRecipe.getPreparation());
            oldRecipe.setName(newRecipe.getName());
            recipeRepository.save(oldRecipe);
            return 1;
        }
        return -1;
    }
}
