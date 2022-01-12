package com.boot.app.board.domain.member;

import com.boot.app.board.domain.Common;
import lombok.Builder;
import lombok.Data;

import java.sql.Date;
import java.time.LocalDateTime;

@Data
@Builder
public class Member extends Common {

    private String email;
    private String password;
    private String name;

}
