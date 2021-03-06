package com.backend.timeforpizza.timeforpizzabackend.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class CommentResponseDTO {

    private Long commentId;

    private String nickname;

    private String comment;

    private String date;

    public CommentResponseDTO() {}
}
