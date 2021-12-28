package com.boot.app.board.domain.member;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MemberService {

    private final MemberMapper memberMapper;


    public void save(Member member){
        memberMapper.insertMember(member);
    }


}
