package com.backend.timeforpizza.timeforpizzabackend.payload;

import java.util.List;

public class RecipeResponse {

    private Long recipeId;

    private String name;

    private String preparation;

    private List<IngredientResponse> ingredients;

    private List<CommentResponse> comments;

    public Long getRecipeId() {
        return recipeId;
    }

    public void setRecipeId(Long recipeId) {
        this.recipeId = recipeId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPreparation() {
        return preparation;
    }

    public void setPreparation(String preparation) {
        this.preparation = preparation;
    }

    public List<IngredientResponse> getIngredients() {
        return ingredients;
    }

    public void setIngredients(List<IngredientResponse> ingredients) {
        this.ingredients = ingredients;
    }

    public List<CommentResponse> getComments() {
        return comments;
    }

    public void setComments(List<CommentResponse> comments) {
        this.comments = comments;
    }

    public RecipeResponse(Long recipeId, String name, String preparation, List<IngredientResponse> ingredients, List<CommentResponse> comments) {
        this.recipeId = recipeId;
        this.name = name;
        this.preparation = preparation;
        this.ingredients = ingredients;
        this.comments = comments;
    }

    public RecipeResponse() {}
}
