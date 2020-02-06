package com.backend.timeforpizza.timeforpizzabackend.service;

import com.backend.timeforpizza.timeforpizzabackend.model.Comment;
import com.backend.timeforpizza.timeforpizzabackend.model.Ingredient;
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
        Recipe r = new Recipe();
        r.setName("Pica");
        r.setPreparation("Do pieca");
        r.setImageUrl("img");

        Ingredient i1 = new Ingredient();
        i1.setUnit("c");
        i1.setAmount(20);
        i1.setName("CAS");
        i1.setRecipe(r);

        Ingredient i2 = new Ingredient();
        i2.setUnit("c2");
        i2.setAmount(202);
        i2.setName("CA2S");
        i2.setRecipe(r);

        List<Ingredient> i = new ArrayList<>();
        i.add(i1); i.add(i2);

        r.setIngredients(i);

        recipeRepository.save(r);

        return 1;

//        return recipeRepository.save(recipe) != null ? 1 : -1;
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
        Recipe oldRecipe = recipeRepository.findById(newRecipe.getRecipeId())
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
