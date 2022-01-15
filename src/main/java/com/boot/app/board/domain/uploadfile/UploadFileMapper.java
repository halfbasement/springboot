package com.boot.app.board.domain.uploadfile;

import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface UploadFileMapper {

    void insertFile(UploadFile uploadFile);

}
