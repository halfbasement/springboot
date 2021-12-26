package com.boot.app.board.web.post.dto;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import java.sql.Date;


@Getter
@Setter
public class PostUpdateRequestDto {

    private Long postId;
    @NotBlank
    private String title;
    @NotBlank
    private String content;
    private String author;
    private Date regDate;
}
