package com.boot.app.board.web.uploadfile.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PostUploadFileDto {


    private String fileName;
    private String path;
    private String uuid;
    private boolean fileType;//이미지 여부

}
