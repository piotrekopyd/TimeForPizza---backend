package com.backend.timeforpizza.timeforpizzabackend.model;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "Recipe")
public class Recipe {
    @Id
    @Column(name = "recipe_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
//    @GenericGenerator(
//            name = "native",
//            strategy = "native"
//    )
    private Integer recipeId;

    private String name;

    private String preparation;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "recipe", targetEntity = Ingredient.class, cascade = CascadeType.PERSIST, orphanRemoval = true)
    private List<Ingredient> ingredients = new ArrayList<>();

    @Column(name = "image_url")
    private String imageUrl;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "recipe", targetEntity = Comment.class, cascade = CascadeType.PERSIST, orphanRemoval = true)
    private List<Comment> comments = new ArrayList<>();

    public Integer getRecipeId() {
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

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public List<Comment> getComments() {
        return comments;
    }

    public void setComments(List<Comment> comments) {
        this.comments = comments;
    }

    public Recipe() {}
}
