package com.backend.timeforpizza.timeforpizzabackend.model;

import javax.persistence.*;

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

    public Long getCuriosityId() {
        return curiosityId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getCuriosity() {
        return curiosity;
    }

    public void setCuriosity(String curiosity) {
        this.curiosity = curiosity;
    }

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
