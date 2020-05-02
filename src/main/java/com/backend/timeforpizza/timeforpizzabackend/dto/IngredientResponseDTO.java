package com.backend.timeforpizza.timeforpizzabackend.dto;

import lombok.Data;

@Data
public class IngredientResponseDTO {

    private Long ingredientId;

    private String name;

    private Integer amount;

    private String unit;

    public IngredientResponseDTO(Long ingredientId, String name, Integer amount, String unit) {
        this.ingredientId = ingredientId;
        this.name = name;
        this.amount = amount;
        this.unit = unit;
    }

    public IngredientResponseDTO() {}
}
