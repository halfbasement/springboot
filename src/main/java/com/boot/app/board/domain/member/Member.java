package com.boot.app.board.domain.member;

import lombok.Builder;
import lombok.Data;

import java.sql.Date;

@Data
@Builder
public class Member {

    private String email;
    private String password;
    private String name;
    private Date regDate;
    private Date modifiedDate;

}
