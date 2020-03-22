package com.backend.timeforpizza.timeforpizzabackend.payload;

import lombok.Data;

import java.util.List;

@Data
public class RecipeResponse {

    private Long recipeId;

    private String name;

    private String preparation;

    private List<IngredientResponse> ingredients;

    private List<CommentResponse> comments;

    public RecipeResponse(Long recipeId, String name, String preparation, List<IngredientResponse> ingredients, List<CommentResponse> comments) {
        this.recipeId = recipeId;
        this.name = name;
        this.preparation = preparation;
        this.ingredients = ingredients;
        this.comments = comments;
    }

    public RecipeResponse() {}
}
