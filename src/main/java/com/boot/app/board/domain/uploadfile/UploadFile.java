package com.boot.app.board.domain.uploadfile;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;

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


    public String getImageUrl(){
        try{
            return URLEncoder.encode(path+"/"+uuid+"_"+fileName,"UTF-8");
        }catch (UnsupportedEncodingException e){
            e.printStackTrace();
        }
        return "";
    }


    public UploadFile(String uuid, String path, String fileName, boolean fileType) {
        this.uuid = uuid;
        this.path = path;
        this.fileName = fileName;
        this.fileType = fileType;
    }
}
