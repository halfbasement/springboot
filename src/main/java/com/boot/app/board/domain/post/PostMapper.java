package com.boot.app.board.domain.post;

import org.apache.ibatis.annotations.Mapper;

import java.util.HashMap;
import java.util.List;
import java.util.Optional;

@Mapper
public interface PostMapper {

    List<Post> selectAllPosts();
    Optional<Post> findByPostId(Long postId);
    Long insertPost(Post post);
    int deletePost(Long postId);
    int updatePost(Post post);

    void viewsCount(Long postId);

    List<Post> selectPostList(HashMap data);
    Integer postCount();


}
