package com.boot.app.board.web.member;

import com.boot.app.board.domain.member.Member;
import com.boot.app.board.domain.member.MemberService;
import com.boot.app.board.web.login.dto.LoginDto;
import com.boot.app.board.web.member.dto.MemberSaveDto;
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
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Slf4j
@RequiredArgsConstructor
@Controller
@RequestMapping("/member")
public class MemberController {

    private final MemberService memberService;

    @GetMapping("/add")
    public String saveMemberForm(Model model){
        model.addAttribute("member",new MemberSaveDto());
        return "member/member_save_form";
    }

    @PostMapping("/add")
    public String saveMember(@Validated @ModelAttribute("member") MemberSaveDto dto, BindingResult bindingResult,
                            RedirectAttributes redirectAttributes){

        //글로벌오류
        if ( !StringUtils.hasText(dto.getPassword()) && !StringUtils.hasText(dto.getEmail()) && !StringUtils.hasText(dto.getName())) {
            bindingResult.reject("globalError");
        }

        //아이디가 중복이면 true
        boolean memberCheck = memberService.validateEmail(dto.getEmail());
        log.info("memberCheck={}",memberCheck);

        if(memberCheck){
            log.info("중복된 이메일  formId={}",dto.getEmail());

            bindingResult.rejectValue("email","sameEmail");
        }



        if(!dto.getPassword().contentEquals(dto.getPasswordConfirm())){

            log.info("password={},passwordConfirm ={}",dto.getPassword(),dto.getPasswordConfirm());

            bindingResult.rejectValue("passwordConfirm","passwordConfirmError");
        }

        if(bindingResult.hasErrors()){
            log.info("Member Error={}",bindingResult);
            return "member/member_save_form";
        }


        Member member = Member.builder()
                .email(dto.getEmail())
                .name(dto.getName())
                .password(dto.getPassword())
                .build();

         memberService.save(member);




        return  "redirect:/login";
    }
}
