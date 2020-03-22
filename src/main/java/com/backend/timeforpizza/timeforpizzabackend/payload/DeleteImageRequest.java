package com.backend.timeforpizza.timeforpizzabackend.payload;

import lombok.Data;

@Data
public class DeleteImageRequest {
    private Long imageId;

    private String imageName;

    public DeleteImageRequest(Long imageId, String imageName) {
        this.imageId = imageId;
        this.imageName = imageName;
    }
}
