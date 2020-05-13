package com.backend.timeforpizza.timeforpizzabackend.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class RecipeResponseDTO {

    private Long recipeId;

    private String name;

    private String preparation;

    private String date;

    private String thumbnailUrl;

    private List<IngredientResponseDTO> ingredients;

    private List<CommentResponseDTO> comments;

    private List<RecipeImageResponseDTO> images;

    public RecipeResponseDTO() {}
}
