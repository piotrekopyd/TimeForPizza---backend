package com.backend.timeforpizza.timeforpizzabackend.controller;

import com.backend.timeforpizza.timeforpizzabackend.dto.*;
import com.backend.timeforpizza.timeforpizzabackend.service.RecipeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping(path = "/recipes")
public class RecipeController {

    private final RecipeService recipeService;

    @Autowired
    public RecipeController(RecipeService recipeService) {
        this.recipeService = recipeService;
    }

    @DeleteMapping(path = "{id}")
    public void deleteRecipe(@PathVariable("id") Long recipeId) {
        recipeService.deleteRecipeById(recipeId);
    }

    @DeleteMapping(path = "/images")
    public void deleteImages(@RequestBody List<DeleteImageRequestDTO> deleteImageRequestDTOS) {
        recipeService.deleteImages(deleteImageRequestDTOS);
    }

    @GetMapping(path = "{recipeId}")
    public RecipeResponseDTO getRecipeById(@PathVariable("recipeId") Long id) {
        return recipeService.getRecipeById(id);
    }

    @GetMapping(path = "/{recipeId}/ingredients")
    public List<IngredientResponseDTO> getIngredientsByRecipeId(@PathVariable("recipeId") Long recipeId) {
        return recipeService.getAllIngredientsByRecipeId(recipeId);
    }

    @GetMapping(path = "/{recipeId}/comments")
    public List<CommentResponseDTO> getCommentsByRecipeId(@PathVariable("recipeId") Long recipeId) {
        return recipeService.getAllCommentsByRecipeId(recipeId);
    }

    @PatchMapping(path = "{recipeId}")
    public RecipeResponseDTO updateRecipe(@RequestBody RecipeRequestDTO recipeRequestDTO, @PathVariable("recipeId") Long id) {
        return recipeService.updateRecipe(recipeRequestDTO, id);
    }

    @PatchMapping(path = "/{recipeId}/thumbnail")
    public RecipeResponseDTO updateRecipeThumbnail(@PathVariable("recipeId") Long id, @RequestParam("thumbnailUrl") String thumbnailUrl) {
        if (thumbnailUrl.isEmpty()) {
            // todo
            return null;
        }
        return recipeService.updateRecipeThumbnail(id, thumbnailUrl);
    }

    @PostMapping
    public RecipeResponseDTO addRecipe(@RequestBody RecipeRequestDTO recipe) {
        return recipeService.addRecipe(recipe);
    }

    @PostMapping(path = "/{recipeId}/comments")
    public CommentResponseDTO addComment(@PathVariable("recipeId") Long recipeId, @RequestBody CommentRequestDTO commentRequestDTO) {
        return recipeService.addComment(recipeId, commentRequestDTO);
    }

    @PostMapping(path = "/{recipeId}/ingredients")
    public IngredientResponseDTO addIngredient(@PathVariable("recipeId") Long recipeId, @RequestBody IngredientRequestDTO ingredientRequestDTO) {
        return recipeService.addIngredient(recipeId, ingredientRequestDTO);
    }

    @PostMapping(path = "{recipeId}/images")
    public void uploadImages(@PathVariable("recipeId") Long recipeId, @RequestParam("files") MultipartFile[] multipartFiles) {
        recipeService.uploadImages(recipeId, multipartFiles);
    }
}