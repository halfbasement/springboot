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

    public int editFile(Long postId){

        //먼저 모든 파일 삭제하고
        int i = uploadFileMapper.deleteByPostId(postId);



        return i;
    }
}
