package com.boot.app.board.domain.member;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class MemberService {

    private final MemberMapper memberMapper;




    public void save(Member member){
        Optional<Member> byEmail = memberMapper.findByEmail(member.getEmail());


        if(byEmail!=null){
            new IllegalArgumentException("중복 아이디 입니다.");
        }

        memberMapper.insertMember(member);
    }

    public Member findByEmail(String email){
      return  memberMapper.findByEmail(email).orElseThrow(()->new IllegalArgumentException("존재하지 않는 회원 입니다"));
    }

    public String validateEmail(String email){

        String findEmail = memberMapper.validateEmail(email);

        if(findEmail==null){
            return "";
        }

        return findEmail;
    }

}
