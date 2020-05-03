package com.backend.timeforpizza.timeforpizzabackend.utils;


import com.backend.timeforpizza.timeforpizzabackend.model.Comment;
import com.backend.timeforpizza.timeforpizzabackend.model.Curiosity;
import com.backend.timeforpizza.timeforpizzabackend.model.Ingredient;
import com.backend.timeforpizza.timeforpizzabackend.model.Recipe;
import com.backend.timeforpizza.timeforpizzabackend.dto.*;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class ModelMapper {
    public static Comment mapCommentRequestToComment(CommentRequestDTO request) {
        Comment comment = new Comment();
        comment.setNickname(request.getNickname());
        comment.setComment(request.getComment());

        return comment;
    }

    public static CommentResponseDTO mapCommentToCommentResponse(Comment comment) {
        CommentResponseDTO response = new CommentResponseDTO();
        response.setComment(comment.getComment());
        response.setCommentId(comment.getCommentId());
        response.setNickname(comment.getNickname());

        return response;
    }

    public static Ingredient mapIngredientRequestToIngredient(IngredientRequestDTO ingredientRequestDTO) {
        Ingredient ingredient = new Ingredient();
        ingredient.setName(ingredientRequestDTO.getName());
        ingredient.setAmount(ingredientRequestDTO.getAmount());
        ingredient.setUnit(ingredientRequestDTO.getUnit());

        return ingredient;
    }

    public static IngredientResponseDTO mapIngredientToIngredientResponse(Ingredient ingredient) {
        IngredientResponseDTO ingredientResponseDTO = new IngredientResponseDTO();
        ingredientResponseDTO.setIngredientId(ingredient.getIngredientId());
        ingredientResponseDTO.setAmount(ingredient.getAmount());
        ingredientResponseDTO.setUnit(ingredient.getUnit());
        ingredientResponseDTO.setName(ingredient.getName());

        return ingredientResponseDTO;
    }

    public static Ingredient mapIngredientResponseToIngredient(IngredientResponseDTO ingredientResponseDTO) {
        Ingredient ingredient = new Ingredient();
        ingredient.setName(ingredientResponseDTO.getName());
        ingredient.setAmount(ingredientResponseDTO.getAmount());
        ingredient.setUnit(ingredientResponseDTO.getUnit());
        ingredient.setIngredientId(ingredientResponseDTO.getIngredientId());

        return ingredient;
    }

    public static Recipe mapRecipeRequestToRecipe(RecipeRequestDTO recipeRequestDTO) {
        Recipe recipe = new Recipe();
        recipe.setName(recipeRequestDTO.getName());
        recipe.setPreparation(recipeRequestDTO.getPreparation());

        List<Ingredient> ingredients = new ArrayList<>();
        recipeRequestDTO.getIngredients().stream()
                .map(ModelMapper::mapIngredientRequestToIngredient)
                .forEach(ingredients::add);
        recipe.setIngredients(ingredients);

        return recipe;
    }

    public static RecipeResponseDTO mapRecipeToRecipeResponse(Recipe recipe) {
        RecipeResponseDTO recipeResponseDTO = new RecipeResponseDTO();
        recipeResponseDTO.setName(recipe.getName());
        recipeResponseDTO.setPreparation(recipe.getPreparation());
        recipeResponseDTO.setRecipeId(recipe.getRecipeId());

        List<CommentResponseDTO> commentResponseDTOList = recipe.getComments().stream()
                .map(ModelMapper::mapCommentToCommentResponse)
                .collect(Collectors.toList());
        recipeResponseDTO.setComments(commentResponseDTOList);

        List<IngredientResponseDTO> ingredientResponseDTOList = recipe.getIngredients().stream()
                .map(ModelMapper::mapIngredientToIngredientResponse)
                .collect(Collectors.toList());
        recipeResponseDTO.setIngredients(ingredientResponseDTOList);

        return recipeResponseDTO;
    }

    public static CuriosityResponseDTO mapCuriosityToCuriosityResponse(Curiosity curiosity) {
        return new CuriosityResponseDTO(curiosity.getCuriosityId(), curiosity.getTitle(), curiosity.getCuriosity(), curiosity.getAuthor());
    }

    public static Curiosity mapCuriosityRequestToCuriosity(CuriosityRequestDTO curiosityRequestDTO) {
        return new Curiosity(curiosityRequestDTO.getTitle(), curiosityRequestDTO.getCuriosity(), curiosityRequestDTO.getAuthor());
    }
}
