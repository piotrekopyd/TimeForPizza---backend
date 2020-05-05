package com.backend.timeforpizza.timeforpizzabackend.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class RecipeImageResponseDTO {

    private Long imageId;

    private String url;

    private String imageName;
}
