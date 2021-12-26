package com.boot.app.board.domain.post;

import com.boot.app.board.web.post.dto.PostDetailDto;
import com.boot.app.board.web.post.dto.PostListDto;
import com.boot.app.board.web.post.dto.PostSaveDto;
import com.boot.app.board.web.post.dto.PostUpdateRequestDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

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
