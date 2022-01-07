package com.boot.app.board.web.comment;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/comment")
public class CommentController {


    @PostMapping("/{postId}")
    public void addComment(@PathVariable Long postId){

    }
}
