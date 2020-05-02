package com.backend.timeforpizza.timeforpizzabackend.dto;

import lombok.Data;

@Data
public class ImageRequestDTO {

    private String imageName;

    private String url;

    private Long recipeId;

    public ImageRequestDTO(String imageName, String url, Long recipeId) {
        this.imageName = imageName;
        this.url = url;
        this.recipeId = recipeId;
    }
}
