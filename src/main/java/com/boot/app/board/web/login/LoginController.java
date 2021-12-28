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

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@Slf4j
@Controller
@RequiredArgsConstructor
public class LoginController {

    private final LoginService loginService;

    @GetMapping("/login")
    public String loginForm(Model model){
        model.addAttribute("login",new LoginDto());
        return "login/login_form";
    }


    @PostMapping("/login")
    public String login(@Validated @ModelAttribute("login") LoginDto dto, BindingResult bindingResult,
                        HttpServletRequest request){

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


        //로그인 성공
        HttpSession session = request.getSession(); // 세션이 있으면 있는 세션 반환 , 없으면 신규 세션 생성
        session.setAttribute(SessionConst.LOGIN_MEMBER,loginResult);


        return "redirect:/";
    }

    @GetMapping("/logout")
    public String logout(HttpServletRequest request){
        HttpSession session = request.getSession(false);
        if(session!=null){
            session.invalidate();
        }
        return "redirect:/";
    }

}
