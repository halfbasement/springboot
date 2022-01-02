package com.boot.app.board.domain.post;

import com.boot.app.board.domain.page.Paging;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Optional;

@Mapper
public interface PostMapper {

    List<Post> selectAllPosts(Paging paging);
    Optional<Post> findByPostId(Long postId);
    Long insertPost(Post post);
    void deletePost(Long postId);
    void updatePost(Post post);
    Integer postCount();
}
