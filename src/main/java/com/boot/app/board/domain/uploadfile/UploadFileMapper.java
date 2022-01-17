package com.boot.app.board.domain.uploadfile;

import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface UploadFileMapper {

    void insertFile(UploadFile uploadFile);
    List<UploadFile> findByPostId(Long postId);
    int deleteByPostId(Long postId);
    List<UploadFile> oldFileByPath();
}
