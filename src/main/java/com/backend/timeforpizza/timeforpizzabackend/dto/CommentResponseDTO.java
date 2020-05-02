package com.backend.timeforpizza.timeforpizzabackend.dto;

import lombok.Data;

@Data
public class CommentResponseDTO {

    private Long commentId;

    private String nickname;

    private String comment;

    public CommentResponseDTO(Long commentId, String nickname, String comment) {
        this.commentId = commentId;
        this.nickname = nickname;
        this.comment = comment;
    }

    public CommentResponseDTO() {}
}
