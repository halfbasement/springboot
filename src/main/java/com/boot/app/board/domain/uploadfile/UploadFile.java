package com.boot.app.board.domain.uploadfile;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UploadFile {

    private Long fileId;
    private String uuid;
    private String path;
    private String fileName;
    private boolean fileType;

    private Long postId;


    public UploadFile(String uuid, String path, String fileName, boolean fileType) {
        this.uuid = uuid;
        this.path = path;
        this.fileName = fileName;
        this.fileType = fileType;
    }
}
