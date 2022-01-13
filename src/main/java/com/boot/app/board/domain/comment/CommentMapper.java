package com.boot.app.board.domain.comment;

import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface CommentMapper {

    List<Comment> findByPostId(Long postId);
    Long insertComment(Comment comment);
    Comment findByCommentId(Long commentId);
    void deleteComment(Long commentId);
    void updateComment(Comment comment);
    Long findCommentCount(Long postId);
}
