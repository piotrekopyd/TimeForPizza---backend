package com.backend.timeforpizza.timeforpizzabackend.payload;

import lombok.Data;

@Data
public class CommentResponse {

    private Long commentId;

    private String nickname;

    private String comment;

    public CommentResponse(Long commentId, String nickname, String comment) {
        this.commentId = commentId;
        this.nickname = nickname;
        this.comment = comment;
    }

    public CommentResponse() {}
}
