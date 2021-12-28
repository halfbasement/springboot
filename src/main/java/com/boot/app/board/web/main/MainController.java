package com.boot.app.board.web.main;

import com.boot.app.board.domain.member.Member;
import com.boot.app.board.web.login.SessionConst;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.SessionAttribute;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

@Controller
public class MainController {

    @GetMapping("/")
    public String main(Model model, @SessionAttribute(name = SessionConst.LOGIN_MEMBER ,required = false) Member loginMember){


        //세션 자체가 없어도 홈으로
        if(loginMember == null){
            return "main";
        }


        model.addAttribute("member",loginMember);

        return "main";
    }
}
