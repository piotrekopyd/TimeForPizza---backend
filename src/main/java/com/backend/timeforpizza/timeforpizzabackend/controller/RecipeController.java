package com.backend.timeforpizza.timeforpizzabackend.controller;

import com.backend.timeforpizza.timeforpizzabackend.payload.*;
import com.backend.timeforpizza.timeforpizzabackend.service.RecipeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
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

    @GetMapping
    public List<RecipeResponse> getAllRecipes() {
        return recipeService.getAllRecipes();
    }

    @GetMapping(path = "{recipeId}")
    public RecipeResponse getRecipeById(@PathVariable("recipeId") Long id) {
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

    @PostMapping(path = "/{recipeId}/comments")
    public CommentResponse addComment(@PathVariable("recipeId") Long recipeId, @NotBlank @RequestBody CommentRequest commentRequest) {
        return recipeService.addComment(recipeId, commentRequest);
    }

    @PostMapping(path = "/{recipeId}/ingredients")
    public IngredientResponse addIngredient(@PathVariable("recipeId") Long recipeId, @NotBlank @RequestBody IngredientRequest ingredientRequest) {
        return recipeService.addIngredient(recipeId, ingredientRequest);
    }

    @PutMapping(path = "{recipeId}")
    public RecipeResponse updateRecipe(@Valid @NotNull @RequestBody RecipeRequest recipeRequest, @PathVariable("recipeId") Long id) {
        return recipeService.updateRecipe(recipeRequest, id);
    }

    @DeleteMapping(path = "{id}")
    public void deleteRecipe(@PathVariable("id") Long recipeId) {
        recipeService.deleteRecipe(recipeId);
    }
}