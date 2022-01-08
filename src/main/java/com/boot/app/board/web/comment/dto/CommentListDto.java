package com.boot.app.board.web.comment.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.sql.Date;
import java.time.LocalDateTime;

@Data
@AllArgsConstructor
public class CommentListDto {

    private String comment;
    private String memberEmail;
    private Long parent;


    private LocalDateTime regDate;


}
