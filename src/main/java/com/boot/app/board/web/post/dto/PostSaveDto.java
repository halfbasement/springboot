package com.boot.app.board.web.post.dto;

import com.boot.app.board.domain.member.Member;
import lombok.*;
import org.hibernate.validator.constraints.Range;
import org.springframework.web.multipart.MultipartFile;
import org.w3c.dom.stylesheets.LinkStyle;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class PostSaveDto {

    @NotBlank
    private String title;

    @NotBlank
    private String content;

    @NotBlank
    private String author;

    @NotNull
    @Range(min=10,max = 1000)
    private Integer number;


    @NotBlank
    private String memberEmail;


    public PostSaveDto(Member member) {
        this.memberEmail = member.getEmail();
        this.author = member.getEmail();
    }
}
