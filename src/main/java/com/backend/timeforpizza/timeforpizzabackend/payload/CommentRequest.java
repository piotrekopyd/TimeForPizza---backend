package com.backend.timeforpizza.timeforpizzabackend.payload;


public class CommentRequest {

    private String nickname;

    private String comment;

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
}
