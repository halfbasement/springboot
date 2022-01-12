package com.boot.app.board.web.post;

import com.boot.app.board.domain.comment.Comment;
import com.boot.app.board.domain.comment.CommentService;
import com.boot.app.board.domain.member.Member;
import com.boot.app.board.domain.post.Post;
import com.boot.app.board.domain.post.PostService;
import com.boot.app.board.web.login.SessionConst;
import com.boot.app.board.web.post.dto.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Slf4j
@RequiredArgsConstructor
@Controller
@RequestMapping("/post")
public class PostController {

    private final PostService postService;

    @GetMapping()
    public String posts(@ModelAttribute("page") Post post, Model model) {






       log.info("count={}",postService.pageCount());
        Integer pageCount = postService.pageCount();



        post.setEndPage(pageCount);



        List<Post> posts = postService.postPageList(post);

        model.addAttribute("posts", posts);
        return "post/post_list";
    }

    @GetMapping("/save")
    public String saveForm(Model model , @SessionAttribute(name = SessionConst.LOGIN_MEMBER ,required = false)Member member) {


        model.addAttribute("post", new PostSaveDto(member));
        return "post/post_save_form";
    }

    @PostMapping("/save")
    public String savePost(@Validated @ModelAttribute("post") PostSaveDto dto, BindingResult bindingResult, RedirectAttributes redirectAttributes) {

        //글로벌오류
        if (dto.getNumber() == null && !StringUtils.hasText(dto.getTitle()) && !StringUtils.hasText(dto.getAuthor()) && !StringUtils.hasText(dto.getContent())) {
            bindingResult.reject("globalError");
        }

        //검증실패시 다시 입력 폼으로
        if (bindingResult.hasErrors()) {

            log.info("errors ={}", bindingResult);
            return "post/post_save_form";
        }


        // postService.save(dto);
        Post entity = Post.builder()
                .title(dto.getTitle())
                .content(dto.getContent())
                .author(dto.getAuthor())
                .number(dto.getNumber())
                .memberEmail(dto.getMemberEmail())
                .build();

        Long savePostId = postService.save(entity);

        redirectAttributes.addAttribute("postId", savePostId);
        redirectAttributes.addAttribute("status", true);

        return "redirect:/post/{postId}";

    }

    @GetMapping("/{postId}/remove")
    public String remove(@PathVariable Long postId, @SessionAttribute(name = SessionConst.LOGIN_MEMBER ,required = false)Member member) {

        Post post = postService.findByPostId(postId);

        if(post.getAuthor().contentEquals(member.getEmail())){
            postService.deletePost(postId);
        }
        return "redirect:/post";
    }

    @GetMapping("/{postId}")
    public String post(@PathVariable Long postId, Model model ,@SessionAttribute(name = SessionConst.LOGIN_MEMBER,required = false)Member member) {

        Post entity = postService.findByPostId(postId);
        PostDetailDto post = new PostDetailDto(entity);

      //  List<Comment> comments = commentService.commentList(postId);



        model.addAttribute("post", post);
      //  model.addAttribute("comments",comments);
        model.addAttribute("member",member);
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
    public String editForm(@Validated @ModelAttribute("post") PostUpdateRequestDto dto, BindingResult bindingResult,@PathVariable Long postId,
                           @SessionAttribute(name = SessionConst.LOGIN_MEMBER , required = false)Member member) {


        //글로벌오류
        if ( !StringUtils.hasText(dto.getTitle())  && !StringUtils.hasText(dto.getContent())) {
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

        if(post.getAuthor().contentEquals(member.getEmail())) {
            postService.updatePost(entity, postId);
        }

        return "redirect:/post/{postId}";
    }
}
