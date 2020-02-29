package com.backend.timeforpizza.timeforpizzabackend.payload;

public class CommentResponse {

    private Long commentId;

    private String nickname;

    private String comment;

    public Long getCommentId() {
        return commentId;
    }

    public void setCommentId(Long commentId) {
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

    public CommentResponse(Long commentId, String nickname, String comment) {
        this.commentId = commentId;
        this.nickname = nickname;
        this.comment = comment;
    }

    public CommentResponse() {}
}
