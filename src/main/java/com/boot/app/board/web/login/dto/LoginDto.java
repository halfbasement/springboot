package com.boot.app.board.web.login.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class LoginDto {

    @NotBlank
    private String email;
    @NotBlank
    private String password;

}
