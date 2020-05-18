package com.backend.timeforpizza.timeforpizzabackend.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class IngredientRequestDTO {

    private String name;

    private Double amount;

    private String unit;
}
