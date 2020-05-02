package com.backend.timeforpizza.timeforpizzabackend.dto;

import lombok.Data;

@Data
public class CuriosityRequestDTO {

    private String title;

    private String curiosity;

    private String author;

    public CuriosityRequestDTO(String title, String curiosity, String author) {
        this.title = title;
        this.curiosity = curiosity;
        this.author = author;
    }
}
