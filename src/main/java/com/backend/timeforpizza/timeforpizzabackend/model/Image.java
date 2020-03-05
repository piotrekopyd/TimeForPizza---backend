package com.backend.timeforpizza.timeforpizzabackend.model;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;

@Entity
public class Image {
    @Id
    @Column(name = "image_url_id")
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private Long imageUrlId;

    @Column(unique = true)
    private String url;

    @Column(name = "image_name")
    private String imageName;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "recipe_id", nullable = false)
    @JsonIgnore
    Recipe recipe;

    public Long getImageUrlId() {
        return imageUrlId;
    }

    public void setImageUrlId(Long imageUrlId) {
        this.imageUrlId = imageUrlId;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public Recipe getRecipe() {
        return recipe;
    }

    public void setRecipe(Recipe recipe) {
        this.recipe = recipe;
    }

    public Image(String url) {
        this.url = url;
    }

    public Image() {
    }
}