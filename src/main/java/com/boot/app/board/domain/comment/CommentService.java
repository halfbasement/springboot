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

    public Comment findOneComment(Long commentId){
        Comment mainComment = commentMapper.findByCommentId(commentId);

        return mainComment;
    }

    public Long commentCount(Long postId){
        Long commentCount = commentMapper.findCommentCount(postId);

        return commentCount;
    }

    public void remove(Long commentId){
        commentMapper.deleteComment(commentId);
    }

    public void update(Comment comment){
        commentMapper.updateComment(comment);
    }

}
