package com.backend.timeforpizza.timeforpizzabackend.payload;

import lombok.Data;

@Data
public class IngredientResponse {

    private Long ingredientId;

    private String name;

    private Integer amount;

    private String unit;

    public IngredientResponse(Long ingredientId, String name, Integer amount, String unit) {
        this.ingredientId = ingredientId;
        this.name = name;
        this.amount = amount;
        this.unit = unit;
    }

    public IngredientResponse() {}
}
