package com.boot.app.post;

import com.boot.app.board.domain.post.Post;
import com.boot.app.board.domain.post.PostService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class pageTest {

    @Autowired
    PostService postService;

    @Test
    void insertPost(){


    }


    @Test
    void pageTest(){

        Post post = new Post();




    }
}
