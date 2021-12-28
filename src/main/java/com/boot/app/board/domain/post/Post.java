package com.boot.app.board.domain.post;

import lombok.Builder;
import lombok.Data;

import java.sql.Date;

@Data
@Builder
public class Post {

    private Long postId;
    private String title;
    private String content;
    private String author;
    private Integer number;
    private Date regDate;
    private Date modifiedDate;
    //fk
    private String memberEmail;

}
