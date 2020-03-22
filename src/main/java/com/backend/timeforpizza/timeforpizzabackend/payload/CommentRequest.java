package com.backend.timeforpizza.timeforpizzabackend.payload;

import lombok.Data;

@Data
public class CommentRequest {

    private String nickname;

    private String comment;

    public CommentRequest(String nickname, String comment) {
        this.nickname = nickname;
        this.comment = comment;
    }

    public CommentRequest() {}
}
