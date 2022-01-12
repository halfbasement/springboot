package com.boot.app.board.web.comment.dto;

import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
public class CommentUpdateDto {

    @NotBlank
    private String memberEmail;
    @NotBlank
    private String comment;


}
