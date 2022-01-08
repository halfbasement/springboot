package com.boot.app.board.domain.comment;

import com.boot.app.board.web.comment.dto.CommentListDto;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;
import java.util.stream.Collectors;

@SpringBootTest
public class CommentTest {
    
    @Autowired
    CommentService commentService;
    
    
    @Test
    void test(){
        List<Comment> comments = commentService.commentList(253L);

        List<CommentListDto> collect = comments.stream()
                .map(comment -> new CommentListDto(comment.getComment(), comment.getMemberEmail(), comment.getParent(), comment.getRegDate()))
                .collect(Collectors.toList());

        collect.stream().forEach(c-> System.out.println("c = " + c.toString()));

        // comments.stream().forEach(comment -> System.out.println("comment.toString() = " + comment.toString()));
        
    }
}
