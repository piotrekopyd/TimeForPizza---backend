package com.backend.timeforpizza.timeforpizzabackend.payload;

import lombok.Data;

@Data
public class IngredientRequest {

    private String name;

    private Integer amount;

    private String unit;

    public IngredientRequest(String name, Integer amount, String unit) {
        this.name = name;
        this.amount = amount;
        this.unit = unit;
    }
}
