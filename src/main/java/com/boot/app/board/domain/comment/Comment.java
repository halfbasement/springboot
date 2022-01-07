package com.boot.app.board.domain.comment;

import lombok.Builder;
import lombok.Data;

import java.sql.Date;

@Data
@Builder
public class Comment {

    private Long commentId;

    private String comment;
    private Long parent;
    private String memberEmail;
    private Long postId;
    private Date regDate;
    private Date modifiedDate;
}
