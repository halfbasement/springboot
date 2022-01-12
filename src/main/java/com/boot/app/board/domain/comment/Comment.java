package com.boot.app.board.domain.comment;

import com.boot.app.board.domain.Common;
import lombok.Builder;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.sql.Date;
import java.time.LocalDateTime;

@Data
@Builder
public class Comment extends Common {

    private Long commentId;

    private String comment;
    private Long parent;
    private String memberEmail;
    private Long postId;
}
