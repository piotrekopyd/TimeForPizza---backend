package com.backend.timeforpizza.timeforpizzabackend.payload;


import com.backend.timeforpizza.timeforpizzabackend.model.Recipe;

public class CommentRequest {

    private String nickname;

    private String comment;

    private Recipe recipe;

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public Recipe getRecipe() {
        return recipe;
    }

    public void setRecipe(Recipe recipe) {
        this.recipe = recipe;
    }

    public CommentRequest(String nickname, String comment) {
        this.nickname = nickname;
        this.comment = comment;
    }

    public CommentRequest(String nickname, String comment, Recipe recipe) {
        this.nickname = nickname;
        this.comment = comment;
        this.recipe = recipe;
    }

    public CommentRequest() {}
}
