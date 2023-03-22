package com.example.temipj.dto.requestDto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;


@Getter
@NoArgsConstructor
@AllArgsConstructor
public class LoginRequestDto {

//    @NotBlank(message = "이메일과 비밀번호를 모두 입력해주세요.")
    private String emailId;

//    @NotBlank(message = "이메일과 비밀번호를 모두 입력해주세요.")
    private String password;
}
