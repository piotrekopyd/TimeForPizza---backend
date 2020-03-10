package com.backend.timeforpizza.timeforpizzabackend.utils;


import com.backend.timeforpizza.timeforpizzabackend.model.Comment;
import com.backend.timeforpizza.timeforpizzabackend.model.Curiosity;
import com.backend.timeforpizza.timeforpizzabackend.model.Ingredient;
import com.backend.timeforpizza.timeforpizzabackend.model.Recipe;
import com.backend.timeforpizza.timeforpizzabackend.payload.*;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class ModelMapper {
    public static Comment mapCommentRequestToComment(CommentRequest request) {
        Comment comment = new Comment();
        comment.setNickname(request.getNickname());
        comment.setComment(request.getComment());
        comment.setRecipe(request.getRecipe());

        return comment;
    }

    public static CommentResponse mapCommentToCommentResponse(Comment comment) {
        CommentResponse response = new CommentResponse();
        response.setComment(comment.getComment());
        response.setCommentId(comment.getCommentId());
        response.setNickname(comment.getNickname());

        return response;
    }

    public static Ingredient mapIngredientRequestToIngredient(IngredientRequest ingredientRequest) {
        Ingredient ingredient = new Ingredient();
        ingredient.setName(ingredientRequest.getName());
        ingredient.setAmount(ingredientRequest.getAmount());
        ingredient.setUnit(ingredientRequest.getUnit());
        ingredient.setRecipe(ingredientRequest.getRecipe());

        return ingredient;
    }

    public static IngredientResponse mapIngredientToIngredientResponse(Ingredient ingredient) {
        IngredientResponse ingredientResponse = new IngredientResponse();
        ingredientResponse.setIngredientId(ingredient.getIngredientId());
        ingredientResponse.setAmount(ingredient.getAmount());
        ingredientResponse.setUnit(ingredient.getUnit());
        ingredientResponse.setName(ingredient.getName());

        return ingredientResponse;
    }

    public static Ingredient mapIngredientResponseToIngredient(IngredientResponse ingredientResponse) {
        Ingredient ingredient = new Ingredient();
        ingredient.setName(ingredientResponse.getName());
        ingredient.setAmount(ingredientResponse.getAmount());
        ingredient.setUnit(ingredientResponse.getUnit());
        ingredient.setIngredientId(ingredientResponse.getIngredientId());

        return ingredient;
    }

    public static Recipe mapRecipeRequestToRecipe(RecipeRequest recipeRequest) {
        Recipe recipe = new Recipe();
        recipe.setName(recipeRequest.getName());
        recipe.setPreparation(recipeRequest.getPreparation());

        List<Ingredient> ingredients = new ArrayList<>();
        recipeRequest.getIngredients().stream()
                .map(ModelMapper::mapIngredientRequestToIngredient)
                .forEach(ingredients::add);
        recipe.setIngredients(ingredients);

        return recipe;
    }

    public static RecipeResponse mapRecipeToRecipeResponse(Recipe recipe) {
        RecipeResponse recipeResponse = new RecipeResponse();
        recipeResponse.setName(recipe.getName());
        recipeResponse.setPreparation(recipe.getPreparation());
        recipeResponse.setRecipeId(recipe.getRecipeId());

        List<CommentResponse> commentResponseList = recipe.getComments().stream()
                .map(ModelMapper::mapCommentToCommentResponse)
                .collect(Collectors.toList());
        recipeResponse.setComments(commentResponseList);

        List<IngredientResponse> ingredientResponseList = recipe.getIngredients().stream()
                .map(ModelMapper::mapIngredientToIngredientResponse)
                .collect(Collectors.toList());
        recipeResponse.setIngredients(ingredientResponseList);

        return recipeResponse;
    }

    public static CuriosityResponse mapCuriosityToCuriosityResponse(Curiosity curiosity) {
        return  new CuriosityResponse(curiosity.getCuriosityId(), curiosity.getTitle(), curiosity.getCuriosity());
    }

    public static Curiosity mapCuriosityRequestToCuriosity(CuriosityRequest curiosityRequest) {
        return new Curiosity(curiosityRequest.getTitle(), curiosityRequest.getCuriosity());
    }
}
