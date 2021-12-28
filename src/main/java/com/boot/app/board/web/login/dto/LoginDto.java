package com.boot.app.board.web.login.dto;

import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
public class LoginDto {

    private String email;
    private String password;

}
