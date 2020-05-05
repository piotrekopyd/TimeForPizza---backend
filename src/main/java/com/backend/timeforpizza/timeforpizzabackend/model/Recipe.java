package com.backend.timeforpizza.timeforpizzabackend.model;

import lombok.Data;

import javax.persistence.*;
import java.time.LocalDate;

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

    @Column(name = "thumbnail_url")
    private String thumbnailUrl;

    private LocalDate date;

    public Recipe() {}
}