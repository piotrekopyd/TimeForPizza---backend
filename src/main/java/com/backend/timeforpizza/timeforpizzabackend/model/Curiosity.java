package com.backend.timeforpizza.timeforpizzabackend.model;

import javax.persistence.*;

@Entity
@Table(name = "Curiosity")
public class Curiosity {
    @Id
    @Column(name = "curiosity_id")
    @GeneratedValue(strategy=GenerationType.AUTO)
    private Integer curiosityId;

    private String title;

    @Column(name = "curiosity", nullable = false)
    private String curiosity;

    public Integer getCuriosityId() {
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
}
