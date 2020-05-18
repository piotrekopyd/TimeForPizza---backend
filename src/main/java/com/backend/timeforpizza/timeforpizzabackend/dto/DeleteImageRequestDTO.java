package com.backend.timeforpizza.timeforpizzabackend.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class DeleteImageRequestDTO {

    private Long imageId;

    private String imageName;
}
