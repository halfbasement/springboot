package com.boot.app.board.domain.post;

import com.boot.app.board.domain.uploadfile.UploadFile;
import com.boot.app.board.domain.uploadfile.UploadFileMapper;
import com.boot.app.board.domain.uploadfile.UploadFileService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.File;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class PostService {

    private final PostMapper postMapper;
    private final UploadFileMapper uploadFileMapper;

    @Transactional
    public Long save(Post entity,List<UploadFile> uploadFiles){

        postMapper.insertPost(entity);

        if(uploadFiles == null || uploadFiles.size() <=0){
            return entity.getPostId();
        }else {
            uploadFiles.forEach(file -> {
                file.setPostId(entity.getPostId());
                uploadFileMapper.insertFile(file);
            });
        }

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

        postMapper.viewsCount(postId);

        return entity;
    }
    public int deletePost(Long postId){
        postMapper.findByPostId(postId)
                .orElseThrow(() -> new IllegalArgumentException("해당 게시물이 없습니다 post_id=" + postId));

       return postMapper.deletePost(postId);

    }

    public void updatePost(Post entity, Long postId){
        postMapper.findByPostId(postId)
                .orElseThrow(() -> new IllegalArgumentException("해당 게시물이 없습니다 post_id=" + postId));

        postMapper.updatePost(entity);

    }
}
