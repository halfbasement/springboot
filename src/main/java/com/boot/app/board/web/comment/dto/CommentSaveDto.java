package com.boot.app.board.web.comment.dto;

import lombok.Data;

@Data
public class CommentSaveDto {

    private String comment;
    private String memberEmail;
    private Long postId;
    private Long parent;
}
