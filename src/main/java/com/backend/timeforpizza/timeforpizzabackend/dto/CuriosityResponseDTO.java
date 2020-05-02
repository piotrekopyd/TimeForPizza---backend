package com.backend.timeforpizza.timeforpizzabackend.dto;

import lombok.Data;

@Data
public class CuriosityResponseDTO {

    private Long curiosityId;

    private String title;

    private String curiosity;

    private String author;

    public CuriosityResponseDTO(Long curiosityId, String title, String curiosity, String author) {
        this.curiosityId = curiosityId;
        this.title = title;
        this.curiosity = curiosity;
        this.author = author;
    }
}
