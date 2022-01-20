package com.boot.app.board.web.post;

import com.boot.app.board.domain.comment.Comment;
import com.boot.app.board.domain.comment.CommentService;
import com.boot.app.board.domain.member.Member;
import com.boot.app.board.domain.post.Post;
import com.boot.app.board.domain.post.PostService;
import com.boot.app.board.domain.uploadfile.UploadFile;
import com.boot.app.board.web.login.SessionConst;
import com.boot.app.board.web.post.dto.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.io.File;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Slf4j
@RequiredArgsConstructor
@Controller
@RequestMapping("/post")
public class PostController {

    private final PostService postService;
    private final CommentService commentService;

    @GetMapping()
    public String posts(@ModelAttribute("page") Post post, Model model) {


        /*log.info("count={}", postService.pageCount());
        Integer pageCount = postService.pageCount();


        post.setMaxPage(pageCount);
*/

        List<Post> posts = postService.postPageList(post);

        model.addAttribute("posts", posts);
        return "post/post_list";
    }

    @GetMapping("/save")
    public String saveForm(Model model, @SessionAttribute(name = SessionConst.LOGIN_MEMBER, required = false) Member member) {


        model.addAttribute("post", new PostSaveDto(member));
        return "post/post_save_form";
    }

    @PostMapping("/save")
    public String savePost(@Validated @ModelAttribute("post") PostSaveDto dto, BindingResult bindingResult, RedirectAttributes redirectAttributes) {

        //글로벌오류
        if (!StringUtils.hasText(dto.getTitle()) && !StringUtils.hasText(dto.getAuthor()) && !StringUtils.hasText(dto.getContent())) {
            bindingResult.reject("globalError");
        }

        //검증실패시 다시 입력 폼으로
        if (bindingResult.hasErrors()) {

            log.info("errors ={}", bindingResult);
            return "post/post_save_form";
        }


        // postService.save(dto);
        Post postEntity = Post.builder()
                .title(dto.getTitle())
                .content(dto.getContent())
                .author(dto.getAuthor())
                //     .number(dto.getNumber())
                .memberEmail(dto.getMemberEmail())
                .build();


        Long savePostId = postService.save(postEntity, dto.getUploadFiles());

        log.info("savePostId={}", savePostId);

        redirectAttributes.addAttribute("postId", savePostId);
        redirectAttributes.addAttribute("status", true);

        return "redirect:/post/{postId}";

    }

    @PostMapping("/{postId}/remove")
    @ResponseBody
    public ResponseEntity<String> remove(@PathVariable Long postId, @SessionAttribute(name = SessionConst.LOGIN_MEMBER, required = false) Member member,
                                         String[] fileName) {


        Post post = postService.findByPostId(postId);


        if (post.getAuthor().contentEquals(member.getEmail())) {


            if (fileName == null) {
                postService.deletePost(postId);
            } else {
                int deleteComplete = postService.deletePost(postId);

                if (deleteComplete == 1) {
                    File file;
                    for (String oneFile : fileName) {
                        try {
                            file = new File("c:\\upload\\" + URLDecoder.decode(oneFile, "UTF-8"));
                            file.delete();

                        } catch (UnsupportedEncodingException e) {
                            e.printStackTrace();
                        }
                    }

                }
            }
        }
        return new ResponseEntity("게시글이 삭제 되었습니다.", HttpStatus.OK);
    }

    @GetMapping("/{postId}")
    public String post(@PathVariable Long postId, Model model, @SessionAttribute(name = SessionConst.LOGIN_MEMBER, required = false) Member member) {

        Post entity = postService.findByPostId(postId);
        PostDetailDto post = new PostDetailDto(entity);


        model.addAttribute("post", post);
        model.addAttribute("member", member);
        return "post/post_detail";
    }

    @GetMapping("/{postId}/edit")
    public String editForm(@PathVariable Long postId, Model model) {

        Post entity = postService.findByPostId(postId);

        PostUpdateRequestDto post = new PostUpdateRequestDto(entity);

        model.addAttribute("post", post);
        return "post/post_edit_form";
    }

    @PostMapping("/{postId}/edit")
    public String editForm(@Validated @ModelAttribute("post") PostUpdateRequestDto dto, BindingResult bindingResult, @PathVariable Long postId,
                           @SessionAttribute(name = SessionConst.LOGIN_MEMBER, required = false) Member member) {


        //글로벌오류
        if (!StringUtils.hasText(dto.getTitle()) && !StringUtils.hasText(dto.getContent())) {
            bindingResult.reject("globalError");
        }

        //검증실패시 다시 입력 폼으로
        if (bindingResult.hasErrors()) {


            log.info("errors ={}", bindingResult);
            return "post/post_edit_form";
        }

        //컨버팅
        Post entity = Post.builder()
                .postId(postId)
                .title(dto.getTitle())
                .content(dto.getContent())
                .build();

        Post post = postService.findByPostId(postId);

        if (post.getAuthor().contentEquals(member.getEmail())) {
            Optional<List<UploadFile>> uploadFiles = Optional.ofNullable(dto.getUploadFiles());

            //제거
            if (uploadFiles.isPresent()) {
                Integer updateComplete = postService.updatePost(entity, postId, uploadFiles.get());

             /*   if (updateComplete != null) {


                    List<String> collect = uploadFiles.get().stream().map(f -> f.getImageUrl()).collect(Collectors.toList());


                    for (String oneFile : collect) {

                        File file;

                        try {
                            file = new File("c:\\upload\\" + URLDecoder.decode(oneFile, "UTF-8"));
                            file.delete();

                        } catch (UnsupportedEncodingException e) {
                            e.printStackTrace();
                        }


                    }

                }*/

            } else {
                postService.updatePost(entity, postId, null);
            }

        }

        return "redirect:/post/{postId}";
    }
}
