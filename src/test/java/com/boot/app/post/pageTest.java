package com.boot.app.post;

import com.boot.app.board.domain.post.Post;
import com.boot.app.board.domain.post.PostService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.stream.IntStream;

@SpringBootTest
public class pageTest {

    @Autowired
    PostService postService;

  /*  @Test
    void insertPost(){


       *//* IntStream.rangeClosed(1,200).forEach(i->{


            Post post = Post.builder()
                    .title("test제목"+i)
                    .content("test내용"+i)
                    .author("test@test.com")
                    .memberEmail("test@test.com")
                    .build();

            postService.save(post,null);

                }

        );*//*


    }*/

/*
    @Test
    void pageTest(){

        Post post = new Post();




    }*/
}
