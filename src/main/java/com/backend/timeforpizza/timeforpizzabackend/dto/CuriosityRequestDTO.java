package com.backend.timeforpizza.timeforpizzabackend.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CuriosityRequestDTO {

    private String title;

    private String curiosity;

    private String author;
}
