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
}
