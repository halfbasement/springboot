package com.boot.app.board.domain.login;

import com.boot.app.board.domain.member.Member;
import com.boot.app.board.domain.member.MemberMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class LoginService {

    private final MemberMapper memberMapper;


    public Member login(Member member){
      return   memberMapper.findByEmail(member.getEmail())
                .filter(m->m.getPassword().equals(member.getPassword()))
                .orElse(null);
    }
}
