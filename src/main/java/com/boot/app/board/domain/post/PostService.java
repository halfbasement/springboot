package com.boot.app.board.domain.post;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class PostService {

    private final PostMapper postMapper;

    public Long save(Post entity){

        postMapper.insertPost(entity);

        return entity.getPostId();

    }


    public List<Post> postList(){
        List<Post> entities = postMapper.selectAllPosts();
        return entities;
    }

    public List<Post> postPageList(Post post){
        List<Post> posts = postMapper.selectPostList(post);
        return posts;
    }

    public Integer pageCount(){



        Integer postCount = postMapper.postCount();
        Integer ceil = (int)Math.ceil(postCount / 20.0);
        System.out.println("ceil = " + ceil);

        return ceil;
    }

    public Post findByPostId(Long postId){
        Post entity = postMapper.findByPostId(postId)
                .orElseThrow(() -> new IllegalArgumentException("해당 게시물이 없습니다 post_id=" + postId));

        return entity;
    }
    public void deletePost(Long postId){
        postMapper.findByPostId(postId)
                .orElseThrow(() -> new IllegalArgumentException("해당 게시물이 없습니다 post_id=" + postId));

        postMapper.deletePost(postId);
    }

    public void updatePost(Post entity, Long postId){
        postMapper.findByPostId(postId)
                .orElseThrow(() -> new IllegalArgumentException("해당 게시물이 없습니다 post_id=" + postId));

        postMapper.updatePost(entity);

    }
}
