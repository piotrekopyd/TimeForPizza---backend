package com.backend.timeforpizza.timeforpizzabackend.payload;

import javassist.runtime.Inner;

public class CuriosityResponse {

    private Inner curiosityId;

    private String title;

    private String curiosity;

    public Inner getCuriosityId() {
        return curiosityId;
    }

    public void setCuriosityId(Inner curiosityId) {
        this.curiosityId = curiosityId;
    }

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
}
