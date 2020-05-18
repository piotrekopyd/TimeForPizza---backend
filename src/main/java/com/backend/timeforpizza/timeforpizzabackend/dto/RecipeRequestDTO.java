package com.backend.timeforpizza.timeforpizzabackend.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RecipeRequestDTO {

    private String name;

    private String preparation;

    private List<IngredientRequestDTO> ingredients;

    private String thumbnailUrl;
}
