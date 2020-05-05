package com.backend.timeforpizza.timeforpizzabackend.utils;


import com.backend.timeforpizza.timeforpizzabackend.model.*;
import com.backend.timeforpizza.timeforpizzabackend.dto.*;

import java.util.List;
import java.util.stream.Collectors;

public class ModelMapper {
    public static Comment mapToComment(CommentRequestDTO request) {
        Comment comment = new Comment();
        comment.setNickname(request.getNickname());
        comment.setComment(request.getComment());

        return comment;
    }

    public static CommentResponseDTO mapToCommentResponse(Comment comment) {
        CommentResponseDTO response = new CommentResponseDTO();
        response.setComment(comment.getComment());
        response.setCommentId(comment.getCommentId());
        response.setNickname(comment.getNickname());
        response.setDate(comment.getDate() != null ? comment.getDate().toString() : null);

        return response;
    }

    public static Ingredient mapToIngredient(IngredientRequestDTO ingredientRequestDTO) {
        Ingredient ingredient = new Ingredient();
        ingredient.setName(ingredientRequestDTO.getName());
        ingredient.setAmount(ingredientRequestDTO.getAmount());
        ingredient.setUnit(ingredientRequestDTO.getUnit());

        return ingredient;
    }

    public static IngredientResponseDTO mapToIngredientResponse(Ingredient ingredient) {
        IngredientResponseDTO ingredientResponseDTO = new IngredientResponseDTO();
        ingredientResponseDTO.setIngredientId(ingredient.getIngredientId());
        ingredientResponseDTO.setAmount(ingredient.getAmount());
        ingredientResponseDTO.setUnit(ingredient.getUnit());
        ingredientResponseDTO.setName(ingredient.getName());

        return ingredientResponseDTO;
    }

    public static Recipe mapToRecipe(RecipeRequestDTO recipeRequestDTO) {
        Recipe recipe = new Recipe();
        recipe.setName(recipeRequestDTO.getName());
        recipe.setPreparation(recipeRequestDTO.getPreparation());
        recipe.setThumbnailUrl(recipeRequestDTO.getThumbnailUrl());

        return recipe;
    }

    public static RecipeResponseDTO mapToRecipeResponse(Recipe recipe, List<Ingredient> ingredients, List<Comment> comments, List<RecipeImage> images) {
        RecipeResponseDTO recipeResponseDTO = new RecipeResponseDTO();
        recipeResponseDTO.setName(recipe.getName());
        recipeResponseDTO.setPreparation(recipe.getPreparation());
        recipeResponseDTO.setRecipeId(recipe.getRecipeId());
        recipeResponseDTO.setDate(recipe.getDate() != null ? recipe.getDate().toString() : null);

        recipeResponseDTO.setComments(comments.stream()
                .map(ModelMapper::mapToCommentResponse)
                .collect(Collectors.toList()));

        recipeResponseDTO.setIngredients(ingredients.stream()
                .map(ModelMapper::mapToIngredientResponse)
                .collect(Collectors.toList()));

        recipeResponseDTO.setImages(images.stream()
                .map(ModelMapper::mapToRecipeImageResponse)
                .collect(Collectors.toList()));

        return recipeResponseDTO;
    }

    public static CuriosityResponseDTO mapToCuriosityResponse(Curiosity curiosity) {
        return new CuriosityResponseDTO(curiosity.getCuriosityId(), curiosity.getTitle(), curiosity.getCuriosity(), curiosity.getAuthor());
    }

    public static RecipeImageResponseDTO mapToRecipeImageResponse(RecipeImage recipeImage) {
        return new RecipeImageResponseDTO(recipeImage.getRecipeImageId(), recipeImage.getUrl(), recipeImage.getImageName());
    }
}
