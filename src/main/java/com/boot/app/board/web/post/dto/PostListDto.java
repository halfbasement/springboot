package com.boot.app.board.web.post.dto;

import com.boot.app.board.domain.post.Post;
import lombok.Data;
import lombok.Getter;

import java.sql.Date;

@Getter
public class PostListDto {

    private Long postId;
    private String title;
    private String author;
    private Date regDate;

    public PostListDto(Post entity){
        this.postId = entity.getPostId();
        this.title = entity.getTitle();
        this.author = entity.getAuthor();
        this.regDate = entity.getRegDate();
    }
}
