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

    public Long save(PostSaveDto dto){

        Post entity = Post.builder()
                .title(dto.getTitle())
                .content(dto.getContent())
                .author(dto.getAuthor())
                .number(dto.getNumber())
                .build();


        postMapper.insertPost(entity);


        return entity.getPostId();

    }

    public List<PostListDto> postList(){
        List<Post> entities = postMapper.selectAllPosts();

        return entities.stream().map(entity -> new PostListDto(entity)).collect(Collectors.toList());
    }

    public PostDetailDto findByPostId(Long postId){
        Post entity = postMapper.findByPostId(postId)
                .orElseThrow(() -> new IllegalArgumentException("해당 게시물이 없습니다 post_id=" + postId));

        return new PostDetailDto(entity);
    }
    public void deletePost(Long postId){

        postMapper.findByPostId(postId)
                .orElseThrow(() -> new IllegalArgumentException("해당 게시물이 없습니다 post_id=" + postId));

        postMapper.deletePost(postId);
    }

    public void updatePost(PostUpdateRequestDto dto, Long postId){
        Post entity =  postMapper.findByPostId(postId)
                .orElseThrow(() -> new IllegalArgumentException("해당 게시물이 없습니다 post_id=" + postId));
        entity.setTitle(dto.getTitle());
        entity.setContent(dto.getContent());

        postMapper.updatePost(entity);

    }
}
