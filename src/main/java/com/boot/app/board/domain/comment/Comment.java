package com.boot.app.board.domain.comment;

import lombok.Builder;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.sql.Date;
import java.time.LocalDateTime;

@Data
@Builder
public class Comment {

    private Long commentId;

    private String comment;
    private Long parent;
    private String memberEmail;
    private Long postId;
    private LocalDateTime regDate;
    private LocalDateTime modifiedDate;
}
