package com.backend.timeforpizza.timeforpizzabackend.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class IngredientRequestDTO {

    private String name;

    private Integer amount;

    private String unit;
}
