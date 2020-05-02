package com.backend.timeforpizza.timeforpizzabackend.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

import javax.persistence.*;

@Entity
@Data
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

    public Image(String imageName, String url) {
        this.imageName = imageName;
        this.url = url;
    }

    public Image() {
    }
}