package com.backend.timeforpizza.timeforpizzabackend.payload;

import java.util.ArrayList;
import java.util.List;

public class RecipeRequest {

    private String name;

    private String preparation;

    private List<IngredientRequest> ingredients = new ArrayList<>();

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

    public List<IngredientRequest> getIngredients() {
        return ingredients;
    }

    public void setIngredients(List<IngredientRequest> ingredients) {
        this.ingredients = ingredients;
    }

    public RecipeRequest(String name, String preparation, List<IngredientRequest> ingredients) {
        this.name = name;
        this.preparation = preparation;
        this.ingredients = ingredients;
    }
}
