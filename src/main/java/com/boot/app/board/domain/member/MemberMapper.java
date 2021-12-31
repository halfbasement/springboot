package com.boot.app.board.domain.member;

import org.apache.ibatis.annotations.Mapper;

import java.util.Optional;

@Mapper
public interface MemberMapper {
    int insertMember(Member member);
    Optional<Member> findByEmail(String email);
}
