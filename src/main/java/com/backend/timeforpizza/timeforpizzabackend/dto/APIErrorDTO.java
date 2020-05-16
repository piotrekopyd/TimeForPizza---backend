package com.backend.timeforpizza.timeforpizzabackend.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.sql.Timestamp;

@Data
@AllArgsConstructor
public class APIErrorDTO {
    private Timestamp timestamp;
    private String message;
    private HttpErrorDTO error;
}
