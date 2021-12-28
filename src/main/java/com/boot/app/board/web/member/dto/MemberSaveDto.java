package com.boot.app.board.web.member.dto;

import lombok.Data;

import javax.validation.constraints.*;

@Data
public class MemberSaveDto {
    @NotBlank
    @Email
    private String email;

    @Pattern(regexp = "^(?=.*[A-Za-z])(?=.*\\d)(?=.*[~!@#$%^&*()+|=])[A-Za-z\\d~!@#$%^&*()+|=]{8,16}$")
    private String password;

    @NotBlank
    private String passwordConfirm;


    @NotBlank
    private String name;

}
