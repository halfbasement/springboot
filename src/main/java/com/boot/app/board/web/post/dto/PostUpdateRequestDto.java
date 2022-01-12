package com.boot.app.board.web.post.dto;

import com.boot.app.board.domain.post.Post;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import java.sql.Date;
import java.sql.Timestamp;
import java.time.LocalDateTime;


@Getter
@Setter
@NoArgsConstructor
public class PostUpdateRequestDto {

    private Long postId;
    @NotBlank
    private String title;
    @NotBlank
    private String content;
    private String author;
    private LocalDateTime regDate;

    public PostUpdateRequestDto(Post entity) {
        this.postId = entity.getPostId();
        this.title = entity.getTitle();
        this.content = entity.getContent();
        this.author = entity.getAuthor();
        this.regDate = entity.getRegDate();
    }
}
