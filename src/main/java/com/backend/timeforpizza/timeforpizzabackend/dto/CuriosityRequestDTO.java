package com.backend.timeforpizza.timeforpizzabackend.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class CuriosityRequestDTO {

    private String title;

    private String curiosity;

    private String author;
}
