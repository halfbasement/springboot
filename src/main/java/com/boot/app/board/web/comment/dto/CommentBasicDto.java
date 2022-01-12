package com.boot.app.board.web.comment.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CommentBasicDto {

    private Long commentId;
    @NotBlank
    private String comment;
    private String memberEmail;
    private Long parent;


    private LocalDateTime modifiedDate;


}
