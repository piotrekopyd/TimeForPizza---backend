package com.backend.timeforpizza.timeforpizzabackend.model;

import lombok.Data;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Data
@Table(name = "Recipe")
public class Recipe {
    @Id
    @Column(name = "recipe_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long recipeId;

    private String name;

    private String preparation;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "recipe")
    private List<Ingredient> ingredients = new ArrayList<>();

    @Column(name = "miniature_image_url")
    private String miniatureImageUrl;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "recipe", orphanRemoval = true)
    @Fetch(FetchMode.SELECT)
    private List<RecipeImage> imagesUrls = new ArrayList<>();

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "recipe", orphanRemoval = true)
    @Fetch(FetchMode.SELECT)
    private List<Comment> comments = new ArrayList<>();

    public Recipe() {}
}