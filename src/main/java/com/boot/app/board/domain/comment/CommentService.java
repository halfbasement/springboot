package com.boot.app.board.domain.comment;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CommentService {
    private final CommentMapper commentMapper;

    public List<Comment> commentList(Long postId){
        List<Comment> comments = commentMapper.findByPostId(postId);

        return comments;
    }

    public Long insertMainComment(Comment comment){
        Long selectKey = commentMapper.insertComment(comment);

        System.out.println("selectKey = " + selectKey);

        System.out.println("comment.getCommentId() = " + comment.getCommentId()); ;

        return comment.getCommentId();
    }

    public Comment findMainComment(Long commentId){
        Comment mainComment = commentMapper.findMainComment(commentId);

        return mainComment;
    }
}
