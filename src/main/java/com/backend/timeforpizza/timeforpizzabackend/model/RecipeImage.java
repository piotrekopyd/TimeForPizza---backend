package com.backend.timeforpizza.timeforpizzabackend.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;

import javax.persistence.*;

@Entity
@Data
@AllArgsConstructor
@Table(name = "RecipeImage")
public class RecipeImage {
    @Id
    @Column(name = "recipe_image_id")
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private Long recipeImageId;

    @Column(unique = true)
    private String url;

    @Column(name = "image_name")
    private String imageName;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "recipe_id", nullable = false)
    @JsonIgnore
    Recipe recipe;

    public RecipeImage() {}
}