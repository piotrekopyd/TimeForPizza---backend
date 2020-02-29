package com.backend.timeforpizza.timeforpizzabackend.model;

import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
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
    private List<ImageUrl> imagesUrls = new ArrayList<>();

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "recipe", orphanRemoval = true)
    @Fetch(FetchMode.SELECT)
    private List<Comment> comments = new ArrayList<>();

    public Long getRecipeId() {
        return recipeId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPreparation() {
        return preparation;
    }

    public void setPreparation(String preparation) {
        this.preparation = preparation;
    }

    public List<Ingredient> getIngredients() {
        return ingredients;
    }

    public void setIngredients(List<Ingredient> ingredients) {
        this.ingredients = ingredients;
    }

    public String getMiniatureImageUrl() {
        return miniatureImageUrl;
    }

    public void setMiniatureImageUrl(String miniatureImageUrl) {
        this.miniatureImageUrl = miniatureImageUrl;
    }

    public List<ImageUrl> getImagesUrls() {
        return imagesUrls;
    }

    public void setImagesUrls(List<ImageUrl> imagesUrls) {
        this.imagesUrls = imagesUrls;
    }

    public List<Comment> getComments() {
        return comments;
    }

    public void setComments(List<Comment> comments) {
        this.comments = comments;
    }

    public Recipe() {}
}