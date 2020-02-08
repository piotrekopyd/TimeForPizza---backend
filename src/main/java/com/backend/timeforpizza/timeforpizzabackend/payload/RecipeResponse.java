package com.backend.timeforpizza.timeforpizzabackend.payload;

import java.util.List;

public class RecipeResponse {

    private Integer ingredientId;

    private String name;

    private String preparation;

    private List<IngredientResponse> ingredients;

    private List<CommentResponse> comments;

    public Integer getIngredientId() {
        return ingredientId;
    }

    public void setIngredientId(Integer ingredientId) {
        this.ingredientId = ingredientId;
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

    public RecipeResponse(Integer ingredientId, String name, String preparation, List<IngredientResponse> ingredients, List<CommentResponse> comments) {
        this.ingredientId = ingredientId;
        this.name = name;
        this.preparation = preparation;
        this.ingredients = ingredients;
        this.comments = comments;
    }
}
