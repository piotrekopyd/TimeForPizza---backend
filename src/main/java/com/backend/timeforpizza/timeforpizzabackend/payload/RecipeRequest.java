package com.backend.timeforpizza.timeforpizzabackend.payload;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class RecipeRequest {

    private String name;

    private String preparation;

    private List<IngredientRequest> ingredients = new ArrayList<>();

    public RecipeRequest(String name, String preparation, List<IngredientRequest> ingredients) {
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
