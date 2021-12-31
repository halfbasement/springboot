package com.boot.app.board.domain.member;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class MemberService {

    private final MemberMapper memberMapper;


    public int save(Member member) {
        Optional<Member> byEmail = memberMapper.findByEmail(member.getEmail());


        if (byEmail != null) {
            new IllegalArgumentException("중복 아이디 입니다.");
        }

        return memberMapper.insertMember(member);
    }

    public Member findByEmail(String email) {
        return memberMapper.findByEmail(email).orElseThrow(() -> new IllegalArgumentException("존재하지 않는 회원 입니다"));
    }

    //찾으면 true 못찾으면 false
    public boolean validateEmail(String email) {
        return memberMapper.findByEmail(email).isPresent();

    }


}
