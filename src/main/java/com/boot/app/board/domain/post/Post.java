package com.boot.app.board.domain.post;

import com.boot.app.board.domain.post.paging.PagingData;
import lombok.Builder;
import lombok.Data;

import java.sql.Date;

@Data
@Builder
public class Post extends PagingData {

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
