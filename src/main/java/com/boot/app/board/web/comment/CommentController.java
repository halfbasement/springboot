package com.boot.app.board.web.comment;

import com.boot.app.board.domain.comment.Comment;
import com.boot.app.board.domain.comment.CommentService;
import com.boot.app.board.domain.member.Member;
import com.boot.app.board.web.comment.dto.CommentBasicDto;
import com.boot.app.board.web.comment.dto.CommentSaveDto;
import com.boot.app.board.web.login.SessionConst;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/comment")
@RequiredArgsConstructor
@Slf4j
public class CommentController {

    private final CommentService commentService;



    @GetMapping("/{postId}")
    public ResponseEntity<Map<String,List<CommentBasicDto>>> CommentList(@PathVariable Long postId) {
        List<Comment> comments = commentService.commentList(postId);

   /*     List<CommentListDto> collect = comments.stream()
                .map(comment -> new CommentListDto(comment.getComment(), comment.getMemberEmail(), comment.getParent(), comment.getRegDate()))
                .collect(Collectors.toList());*/


        List<CommentBasicDto> mainComment = comments.stream().filter(c -> c.getParent() == null)
                .map(c -> new CommentBasicDto(c.getCommentId(), c.getComment(), c.getMemberEmail(), c.getParent(), c.getRegDate()))
                .collect(Collectors.toList());

        List<CommentBasicDto> subComment = comments.stream().filter(c -> c.getParent() != null)
                .map(c -> new CommentBasicDto(c.getCommentId(), c.getComment(), c.getMemberEmail(), c.getParent(), c.getRegDate()))
                .collect(Collectors.toList());


       Map<String,List<CommentBasicDto>> result = new HashMap<>();
        result.put("main",mainComment);
        result.put("sub",subComment);



        return new ResponseEntity<>(result,HttpStatus.OK);
    }

    @GetMapping("/{postId}/modal")
    public ResponseEntity<Map<String,Object>> CommentModalInfo(@PathVariable Long postId, @SessionAttribute(name = SessionConst.LOGIN_MEMBER, required = false) Member member){


        Map<String,Object> result = new HashMap<>();

        result.put("postId",postId);
        result.put("memberEmail",member.getEmail());



        return new ResponseEntity<>(result,HttpStatus.OK);
    }

    @PostMapping//ResponseEntity로 selectkey값 받아와서 넘겨줌
    public ResponseEntity<CommentBasicDto> addComment(@RequestBody CommentSaveDto dto){

        Comment mainComment = Comment.builder()
                .comment(dto.getComment())
                .memberEmail(dto.getMemberEmail())
                .postId(dto.getPostId())
                .build();

        Long saveCommentId = commentService.insertMainComment(mainComment);

        Comment findMain = commentService.findMainComment(saveCommentId);

        CommentBasicDto result = new CommentBasicDto(findMain.getCommentId(), findMain.getComment(), findMain.getMemberEmail(), findMain.getParent(), findMain.getRegDate());


        System.out.println("result = " + result.toString());

        return new ResponseEntity<>(result,HttpStatus.OK);
    }
}
