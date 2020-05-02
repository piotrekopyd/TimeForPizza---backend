package com.backend.timeforpizza.timeforpizzabackend.dto;

import lombok.Data;

@Data
public class CommentRequestDTO {

    private String nickname;

    private String comment;

    public CommentRequestDTO(String nickname, String comment) {
        this.nickname = nickname;
        this.comment = comment;
    }

    public CommentRequestDTO() {}
}
