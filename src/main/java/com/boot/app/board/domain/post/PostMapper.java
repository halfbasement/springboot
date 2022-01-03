package com.boot.app.board.domain.post;

import com.boot.app.board.domain.paging.Criteria;
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

    List<Post> selectPostList(Criteria criteria);
    Integer postCount();
}
