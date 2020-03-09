package com.backend.timeforpizza.timeforpizzabackend.controllers;

import com.backend.timeforpizza.timeforpizzabackend.payload.*;
import com.backend.timeforpizza.timeforpizzabackend.service.RecipeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.List;

@RestController
@RequestMapping(path = "/recipes")
public class RecipeController {
    private final RecipeService recipeService;

    @Autowired
    public RecipeController(RecipeService recipeService) {
        this.recipeService = recipeService;
    }

    @GetMapping(path = "/all")
    public List<RecipeResponse> getAllRecipes() {
        return recipeService.getAllRecipes();
    }

    @GetMapping(path = "{id}")
    public RecipeResponse getRecipeById(@PathVariable("id") Long id) {
        return recipeService.getRecipeById(id);
    }

    @GetMapping(path = "/{recipeId}/ingredients")
    public List<IngredientResponse> getIngredientsByRecipeId(@PathVariable("recipeId") Long recipeId) {
        return recipeService.getAllIngredientsByRecipeId(recipeId);
    }

    @GetMapping(path = "/{recipeId}/comments")
    public List<CommentResponse> getCommentsByRecipeId(@PathVariable("recipeId") Long recipeId) {
        return recipeService.getAllCommentsByRecipeId(recipeId);
    }

    @PostMapping
    public RecipeResponse addRecipe(@Valid @NotNull @RequestBody RecipeRequest recipe) {
        return recipeService.addRecipe(recipe);
    }

    @PutMapping(path = "{id}")
    public boolean updateRecipe(@Valid @NotNull @RequestBody RecipeRequest recipeRequest, @PathVariable Long id) {
        return recipeService.updateRecipe(recipeRequest, id) > 0;
    }

}