package com.boot.app.board.web.comment.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
public class CommentBasicDto {

    private Long commentId;
    private String comment;
    private String memberEmail;
    private Long parent;


    private LocalDateTime regDate;


}
