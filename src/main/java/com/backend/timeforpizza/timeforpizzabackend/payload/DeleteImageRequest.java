package com.backend.timeforpizza.timeforpizzabackend.payload;

public class DeleteImageRequest {
    private Long imageId;

    private String imageName;

    public Long getImageId() {
        return imageId;
    }

    public void setImageId(Long imageId) {
        this.imageId = imageId;
    }

    public String getImageName() {
        return imageName;
    }

    public void setImageName(String imageName) {
        this.imageName = imageName;
    }

    public DeleteImageRequest(Long imageId, String imageName) {
        this.imageId = imageId;
        this.imageName = imageName;
    }
}
