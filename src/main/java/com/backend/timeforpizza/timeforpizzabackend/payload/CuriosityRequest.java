package com.backend.timeforpizza.timeforpizzabackend.payload;

import lombok.Data;

@Data
public class CuriosityRequest {

    private String title;

    private String curiosity;

    public CuriosityRequest(String title, String curiosity) {
        this.title = title;
        this.curiosity = curiosity;
    }
}
