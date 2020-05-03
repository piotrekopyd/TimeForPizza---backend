package com.backend.timeforpizza.timeforpizzabackend.model;

import lombok.AllArgsConstructor;
import lombok.Data;

import javax.persistence.*;

@Data
@AllArgsConstructor
@Entity
@Table(name = "Curiosity")
public class Curiosity {
    @Id
    @Column(name = "curiosity_id")
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private Long curiosityId;

    private String title;

    private String curiosity;

    private String author;

    public Curiosity(){}

    public Curiosity(String title, String curiosity, String author) {
        this.title = title;
        this.curiosity = curiosity;
        this.author = author;
    }
}
