package com.backend.timeforpizza.timeforpizzabackend.dto;

import lombok.Data;

import java.util.List;

@Data
public class RecipeResponseDTO {

    private Long recipeId;

    private String name;

    private String preparation;

    private List<IngredientResponseDTO> ingredients;

    private List<CommentResponseDTO> comments;

    public RecipeResponseDTO(Long recipeId, String name, String preparation, List<IngredientResponseDTO> ingredients, List<CommentResponseDTO> comments) {
        this.recipeId = recipeId;
        this.name = name;
        this.preparation = preparation;
        this.ingredients = ingredients;
        this.comments = comments;
    }

    public RecipeResponseDTO() {}
}
