package com.boot.app.board.domain.comment;

import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface CommentMapper {

    List<Comment> findByPostId(Long postId);
    void insertMainComment(Comment comment);
}
