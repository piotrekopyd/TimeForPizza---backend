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
    public RecipeResponse getRecipeById(@PathVariable("id") Integer id) {
        return recipeService.getRecipeById(id);
    }

    @GetMapping(path = "/{recipeId}/ingredients")
    public List<IngredientResponse> getIngredientsByRecipeId(@PathVariable("recipeId") Integer recipeId) {
        return recipeService.getAllIngredientsByRecipeId(recipeId);
    }

    @GetMapping(path = "/{recipeId}/comments")
    public List<CommentResponse> getCommentsByRecipeId(@PathVariable("recipeId") Integer recipeId) {
        return recipeService.getAllCommentsByRecipeId(recipeId);
    }

    @PostMapping
    public boolean addRecipe(@Valid @NotNull @RequestBody RecipeRequest recipe) {
        return recipeService.addRecipe(recipe) > 0;
    }

    @PostMapping(path = "/{recipeId}/comments")
    public boolean addComment(@Valid @NotNull @RequestBody CommentRequest commentRequest, @PathVariable("recipeId") Integer recipeId) {
        return recipeService.addComment(commentRequest, recipeId) > 0;
    }

    @PutMapping(path = "{id}")
    public boolean updateRecipe(@Valid @NotNull @RequestBody RecipeRequest recipeRequest, @PathVariable Integer id) {
        return recipeService.updateRecipe(recipeRequest, id) > 0;
    }

}