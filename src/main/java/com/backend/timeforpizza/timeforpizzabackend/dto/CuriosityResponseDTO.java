package com.backend.timeforpizza.timeforpizzabackend.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class CuriosityResponseDTO {

    private Long curiosityId;

    private String title;

    private String curiosity;

    private String author;
}
