package com.boot.app.board.web.comment.dto;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Data
public class CommentSaveDto {

    @NotBlank
    private String comment;
    @NotBlank
    private String memberEmail;
    private Long postId;
    private Long parent;
}
