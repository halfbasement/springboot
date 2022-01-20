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
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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

    public List<Post> postPageList(int displayPost,  int postNum){

        HashMap data = new HashMap();
        data.put("displayPost",displayPost);
        data.put("postNum",postNum);

        List<Post> posts = postMapper.selectPostList(data);
        return posts;
    }

    public Integer pageCount(){



        Integer postCount = postMapper.postCount();

        return postCount;
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

    @Transactional
    public int updatePost(Post entity, Long postId ,List<UploadFile> uploadFiles ){

        if(uploadFiles==null || uploadFiles.size()<=0){

            uploadFileMapper.deleteByPostId(postId);
            return   postMapper.updatePost(entity);
        }else{

            Integer i = uploadFileMapper.deleteByPostId(postId);

            if(i != null){
                uploadFiles.forEach(file -> {
                    file.setPostId(entity.getPostId());
                    uploadFileMapper.insertFile(file);
                });
            }

            return postMapper.updatePost(entity);
        }

    }
}
