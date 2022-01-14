package com.boot.app.board.domain.uploadfile;

import lombok.Data;

@Data
public class UploadFile {

    private Long fileId;
    private String uuid;
    private String path;
    private String fileName;
    private boolean fileType;

    private Long postId;

}
