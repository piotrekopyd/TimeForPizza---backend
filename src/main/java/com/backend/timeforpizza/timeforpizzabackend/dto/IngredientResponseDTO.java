package com.backend.timeforpizza.timeforpizzabackend.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class IngredientResponseDTO {

    private Long ingredientId;

    private String name;

    private Double amount;

    private String unit;

    public IngredientResponseDTO() {}
}
