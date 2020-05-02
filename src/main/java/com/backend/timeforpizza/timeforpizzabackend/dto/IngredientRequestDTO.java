package com.backend.timeforpizza.timeforpizzabackend.dto;

import lombok.Data;

@Data
public class IngredientRequestDTO {

    private String name;

    private Integer amount;

    private String unit;

    public IngredientRequestDTO(String name, Integer amount, String unit) {
        this.name = name;
        this.amount = amount;
        this.unit = unit;
    }
}
