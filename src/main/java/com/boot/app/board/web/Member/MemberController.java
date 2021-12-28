package com.boot.app.board.web.Member;

import com.boot.app.board.domain.member.Member;
import com.boot.app.board.domain.member.MemberService;
import com.boot.app.board.web.Member.dto.MemberSaveDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

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
    public String saveMember(@Validated @ModelAttribute("member") MemberSaveDto dto, BindingResult bindingResult){



        Member member = Member.builder()
                .email(dto.getEmail())
                .name(dto.getName())
                .password(dto.getPassword())
                .build();

        memberService.save(member);

        return  "redirect:/";
    }
}
