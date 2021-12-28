package com.boot.app.board.web.login;

import com.boot.app.board.web.login.dto.LoginDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Slf4j
@Controller
@RequestMapping("/login")
public class LoginController {

    @GetMapping
    public String loginForm(Model model){
        model.addAttribute("login",new LoginDto());
        return "login/login_form";
    }


    @PostMapping
    public String login(@ModelAttribute LoginDto dto){

        return "login/login_form";
    }


}
