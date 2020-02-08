package com.backend.timeforpizza.timeforpizzabackend.payload;


public class CuriosityResponse {

    private Integer curiosityId;

    private String title;

    private String curiosity;

    public Integer getCuriosityId() {
        return curiosityId;
    }

    public void setCuriosityId(Integer curiosityId) {
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

    public CuriosityResponse(Integer curiosityId, String title, String curiosity) {
        this.curiosityId = curiosityId;
        this.title = title;
        this.curiosity = curiosity;
    }

    public CuriosityResponse() {}
}
