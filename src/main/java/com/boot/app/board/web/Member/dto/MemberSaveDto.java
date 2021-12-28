package com.boot.app.board.web.Member.dto;

import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
public class MemberSaveDto {
    private String email;
    private String password;
    private String passwordConfirm;
    private String name;
}
