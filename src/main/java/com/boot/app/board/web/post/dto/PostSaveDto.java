package com.boot.app.board.web.post.dto;

import com.boot.app.board.domain.member.Member;
import com.boot.app.board.domain.uploadfile.UploadFile;
import com.boot.app.board.web.uploadfile.dto.PostUploadFileDto;
import lombok.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.NotBlank;
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

/*
    @NotNull
    @Range(min=10,max = 1000)
    private Integer number;
*/


    @NotBlank
    private String memberEmail;

    private List<UploadFile> uploadFiles;

    public PostSaveDto(Member member) {
        this.memberEmail = member.getEmail();
        this.author = member.getEmail();
    }
}
