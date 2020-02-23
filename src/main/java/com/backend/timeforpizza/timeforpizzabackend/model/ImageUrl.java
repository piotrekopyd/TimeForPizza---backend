package com.backend.timeforpizza.timeforpizzabackend.model;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;

@Entity
public class ImageUrl {
    @Column(name = "image_url_id")
    Integer imageUrlId;

    String url;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "recipe_id", nullable = false)
    @JsonIgnore
    Recipe recipe;

    public Integer getImageUrlId() {
        return imageUrlId;
    }

    public void setImageUrlId(Integer imageUrlId) {
        this.imageUrlId = imageUrlId;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
