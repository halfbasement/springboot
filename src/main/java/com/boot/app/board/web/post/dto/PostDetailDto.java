package com.boot.app.board.web.post.dto;

import com.boot.app.board.domain.post.Post;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import java.sql.Date;
@Getter
public class PostDetailDto {

    private Long postId;
    private String title;
    private String content;
    private String author;
    private Date regDate;


    public PostDetailDto(Post entity) {
        this.postId = entity.getPostId();
        this.title = entity.getTitle();
        this.content = entity.getContent();
        this.author = entity.getAuthor();
        this.regDate = entity.getRegDate();
    }
}
