package com.boot.app.board.domain.post;

import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Optional;

@Mapper
public interface PostMapper {

    List<Post> selectAllPosts();
    Optional<Post> findByPostId(Long postId);
    Long insertPost(Post post);
    void deletePost(Long postId);
    void updatePost(Post post);

    void viewsCount(Long postId);

    List<Post> selectPostList(Post post);
    Integer postCount();


}
