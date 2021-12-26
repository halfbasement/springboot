package com.boot.app.board.web.post;

import com.boot.app.board.domain.post.PostService;
import com.boot.app.board.web.common.validate.PostSaveValidator;
import com.boot.app.board.web.post.dto.PostDetailDto;
import com.boot.app.board.web.post.dto.PostListDto;
import com.boot.app.board.web.post.dto.PostSaveDto;
import com.boot.app.board.web.post.dto.PostUpdateRequestDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.thymeleaf.model.IModel;

import java.util.List;

@Slf4j
@RequiredArgsConstructor
@Controller
@RequestMapping("/post")
public class PostController {

    private final PostService postService;
    private final PostSaveValidator postSaveValidator;

  /*  @InitBinder
    public void init(WebDataBinder dataBinder){
        log.info("실행됨");
        dataBinder.addValidators(postSaveValidator);
    }*/


    @GetMapping
    public String posts(Model model) {
        List<PostListDto> posts = postService.postList();
        model.addAttribute("posts", posts);
        return "post/post_list";
    }

    @GetMapping("/save")
    public String saveForm(Model model) {
        model.addAttribute("post", new PostSaveDto());
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
        Long savePostId = postService.save(dto);

        redirectAttributes.addAttribute("postId", savePostId);
        redirectAttributes.addAttribute("status", true);

        return "redirect:/post/{postId}";

    }

    @GetMapping("/{postId}/remove")
    public String remove(@PathVariable Long postId) {

        postService.deletePost(postId);

        return "redirect:/post";
    }

    @GetMapping("/{postId}")
    public String post(@PathVariable Long postId, Model model) {

        PostDetailDto post = postService.findByPostId(postId);

        model.addAttribute("post", post);
        return "post/post_detail";
    }

    @GetMapping("/{postId}/edit")
    public String editForm(@PathVariable Long postId, Model model) {

        PostDetailDto post = postService.findByPostId(postId);

        model.addAttribute("post", post);
        return "post/post_edit_form";
    }

    @PostMapping("/{postId}/edit")
    public String editForm(@PathVariable Long postId, @Validated @ModelAttribute("post") PostUpdateRequestDto dto, BindingResult bindingResult) {


        //글로벌오류
        if ( !StringUtils.hasText(dto.getTitle())  && !StringUtils.hasText(dto.getContent())) {
            bindingResult.reject("globalError");
        }

        //검증실패시 다시 입력 폼으로
        if (bindingResult.hasErrors()) {


            log.info("errors ={}", bindingResult);
            return "post/post_edit_form";
        }

        postService.updatePost(dto, postId);

        return "redirect:/post/{postId}";
    }
}
