package com.backend.timeforpizza.timeforpizzabackend.dto;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class RecipeRequestDTO {

    private String name;

    private String preparation;

    private List<IngredientRequestDTO> ingredients = new ArrayList<>();

    public RecipeRequestDTO(String name, String preparation, List<IngredientRequestDTO> ingredients) {
        this.name = name;
        this.preparation = preparation;
        this.ingredients = ingredients;
    }

    @Override
    public String toString() {
        return "RecipeRequest{" +
                "name='" + name + '\'' +
                ", preparation='" + preparation + '\'' +
                ", ingredients=" + ingredients +
                '}';
    }
}
