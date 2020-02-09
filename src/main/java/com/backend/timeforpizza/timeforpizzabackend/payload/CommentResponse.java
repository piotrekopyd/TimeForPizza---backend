package com.backend.timeforpizza.timeforpizzabackend.payload;

public class CommentResponse {

    private Integer commentId;

    private String nickname;

    private String comment;

    public Integer getCommentId() {
        return commentId;
    }

    public void setCommentId(Integer commentId) {
        this.commentId = commentId;
    }

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

    public CommentResponse(Integer commentId, String nickname, String comment) {
        this.commentId = commentId;
        this.nickname = nickname;
        this.comment = comment;
    }

    public CommentResponse() {}
}
