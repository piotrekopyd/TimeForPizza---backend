package com.backend.timeforpizza.timeforpizzabackend.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class RecipeMiniatureResponseDTO {

    private Long recipeId;

    private String name;

    private String thumbnailUrl;

    private String date;
}
