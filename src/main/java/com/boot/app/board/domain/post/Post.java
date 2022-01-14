package com.boot.app.board.domain.post;

import com.boot.app.board.domain.Common;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Post extends Common {

    private Long postId;
    private String title;
    private String content;
    private String author;
    private Integer number;
    private Long views;
    //fk
    private String memberEmail;



}
