package com.boot.app.board.web.login;

import com.boot.app.board.domain.login.LoginService;
import com.boot.app.board.domain.member.Member;
import com.boot.app.board.domain.member.MemberMapper;
import com.boot.app.board.domain.member.MemberService;
import com.boot.app.board.web.login.dto.LoginDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Slf4j
@Controller
@RequiredArgsConstructor
@RequestMapping("/login")
public class LoginController {

    private final LoginService loginService;

    @GetMapping
    public String loginForm(Model model){
        model.addAttribute("login",new LoginDto());
        return "login/login_form";
    }


    @PostMapping
    public String login(@Validated @ModelAttribute("login") LoginDto dto, BindingResult bindingResult){

        //글로벌오류
        if ( !StringUtils.hasText(dto.getPassword()) &&  !StringUtils.hasText(dto.getEmail())) {
            bindingResult.reject("globalError");
        }

        if(bindingResult.hasErrors()){
            log.info("login error={}",bindingResult);
            return "login/login_form";
        }

        Member member = Member.builder()
                .email(dto.getEmail())
                .password(dto.getPassword())
                .build();


        Member loginResult = loginService.login(member);

        if(loginResult==null){
            bindingResult.reject("loginError","아이디 또는 비밀번호가 맞지 않습니다.");
            return "login/login_form";
        }


        return "redirect:/";
    }


}
