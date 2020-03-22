package com.backend.timeforpizza.timeforpizzabackend.payload;

import lombok.Data;

@Data
public class ImageRequest {

    private String imageName;

    private String url;

    private Long recipeId;

    public ImageRequest(String imageName, String url, Long recipeId) {
        this.imageName = imageName;
        this.url = url;
        this.recipeId = recipeId;
    }
}
