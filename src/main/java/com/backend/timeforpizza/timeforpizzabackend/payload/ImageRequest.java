package com.backend.timeforpizza.timeforpizzabackend.payload;

public class ImageRequest {

    private String imageName;

    private String url;

    private Long recipeId;

    public String getImageName() {
        return imageName;
    }

    public void setImageName(String imageName) {
        this.imageName = imageName;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public Long getRecipeId() {
        return recipeId;
    }

    public void setRecipeId(Long recipeId) {
        this.recipeId = recipeId;
    }

    public ImageRequest(String imageName, String url, Long recipeId) {
        this.imageName = imageName;
        this.url = url;
        this.recipeId = recipeId;
    }
}
