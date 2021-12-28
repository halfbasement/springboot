package com.boot.app.board.domain.member;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class MemberService {

    private final MemberMapper memberMapper;


    public void save(Member member){
        memberMapper.insertMember(member);
    }

    public Member findByEmail(String email){
      return  memberMapper.findByEmail(email).orElseThrow(()->new IllegalArgumentException("존재하지 않는 회원 입니다"));
    }

}
