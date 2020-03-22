package com.backend.timeforpizza.timeforpizzabackend.model;

import lombok.Data;

import javax.persistence.*;

@Data
@Entity
@Table(name = "Curiosity")
public class Curiosity {
    @Id
    @Column(name = "curiosity_id")
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private Long curiosityId;

    private String title;

    @Column(name = "curiosity")
    private String curiosity;

    public Curiosity(){}

    public Curiosity(String title, String curiosity) {
        this.title = title;
        this.curiosity = curiosity;
    }

    public Curiosity(Long curiosityId, String title, String curiosity) {
        this.curiosityId = curiosityId;
        this.title = title;
        this.curiosity = curiosity;
    }
}
