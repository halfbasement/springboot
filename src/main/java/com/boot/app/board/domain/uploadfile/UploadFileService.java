package com.boot.app.board.domain.uploadfile;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UploadFileService {

    private final UploadFileMapper uploadFileMapper;

    public List<UploadFile> findByPostIdFile(Long postId){

        List<UploadFile> byPostId = uploadFileMapper.findByPostId(postId);

        return byPostId;
    }

    public int removeFile(Long fileId){
        int i = uploadFileMapper.deleteFile(fileId);

        return i;
    }
}
