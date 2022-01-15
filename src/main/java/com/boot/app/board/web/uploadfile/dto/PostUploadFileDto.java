package com.boot.app.board.web.uploadfile.dto;

import lombok.Data;

@Data
public class PostUploadFileDto {


    private String fileName;
    private String path;
    private String uuid;
    private boolean fileType;//이미지 여부

}
