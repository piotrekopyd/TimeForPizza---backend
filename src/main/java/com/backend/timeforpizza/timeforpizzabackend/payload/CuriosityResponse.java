package com.backend.timeforpizza.timeforpizzabackend.payload;

import lombok.Data;

@Data
public class CuriosityResponse {

    private Long curiosityId;

    private String title;

    private String curiosity;

    public CuriosityResponse(Long curiosityId, String title, String curiosity) {
        this.curiosityId = curiosityId;
        this.title = title;
        this.curiosity = curiosity;
    }

    public CuriosityResponse() {}
}
