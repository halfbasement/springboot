package com.boot.app.board.web.comment;

import com.boot.app.board.domain.comment.Comment;
import com.boot.app.board.domain.comment.CommentService;
import com.boot.app.board.web.comment.dto.CommentListDto;
import com.boot.app.board.web.comment.dto.CommentSaveDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@RestController
@RequestMapping("/comment")
@RequiredArgsConstructor
@Slf4j
public class CommentController {

    private final CommentService commentService;



    @GetMapping("/{postId}")
    public ResponseEntity<List<CommentListDto>> CommentList(@PathVariable Long postId) {
        List<Comment> comments = commentService.commentList(postId);

        List<CommentListDto> collect = comments.stream()
                .map(comment -> new CommentListDto(comment.getComment(), comment.getMemberEmail(), comment.getParent(), comment.getRegDate()))
                .collect(Collectors.toList());

       /* Map<String,Object> result = new HashMap<>();
        result.put("comments",collect);
*/
        log.info("collect={}",collect);

        return new ResponseEntity<>(collect,HttpStatus.OK);
    }


    @PostMapping//ResponseEntity로 selectkey값 받아와서 넘겨줌
    public ResponseEntity<String> addComment(@RequestBody CommentSaveDto dto){

        Comment mainComment = Comment.builder()
                .comment(dto.getComment())
                .memberEmail(dto.getMemberEmail())
                .postId(dto.getPostId())
                .build();

        commentService.insertMainComment(mainComment);

        return new ResponseEntity<>("SEX",HttpStatus.OK);
    }
}
