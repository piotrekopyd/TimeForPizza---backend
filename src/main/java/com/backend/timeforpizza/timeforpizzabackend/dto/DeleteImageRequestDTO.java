package com.backend.timeforpizza.timeforpizzabackend.dto;

import lombok.Data;

@Data
public class DeleteImageRequestDTO {

    private Long imageId;

    private String imageName;

    public DeleteImageRequestDTO(Long imageId, String imageName) {
        this.imageId = imageId;
        this.imageName = imageName;
    }
}
