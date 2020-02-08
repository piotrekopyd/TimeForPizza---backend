package com.backend.timeforpizza.timeforpizzabackend.payload;

public class CuriosityRequest {

    private String title;

    private String curiosity;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getCuriosity() {
        return curiosity;
    }

    public void setCuriosity(String curiosity) {
        this.curiosity = curiosity;
    }

    public CuriosityRequest(String title, String curiosity) {
        this.title = title;
        this.curiosity = curiosity;
    }
}
