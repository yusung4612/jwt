package com.example.temipj.dto.requestDto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class MemberRequestDto {

//    @NotBlank(message = "이름을 입력해주세요.")
//    @Size(min=1,max=10, message= "이름은 최소 1자이상 최대 10자미만으로 입력해주세요.")
//    @Pattern(regexp = "[a-zA-Zㄱ-ㅎ가-힣]*${1,10}", message = "이름 형식을 확인해 주세요.")
    private String membername;

//    @NotBlank(message = "이메일을 입력해주세요.")
//    @Size(min=8,max=30, message= "8자리이상 30자리 미만 글자로 email를 만들어주세요")
//    @Pattern(regexp = "^[0-9a-zA-Z]+@[a-zA-Z]+\\.[a-zA-Z]+$" , message = "이메일 형식을 확인해 주세요.") // 수정하기
    private String emailId;

//    @NotBlank(message = "비밀번호를 입력해주세요.")
//    @Size(min=4,max=32, message= "비밀번호는 최소 4자이상 최대 20자미만으로 입력해주세요.")
//    @Pattern(regexp = "^(?=.*[a-z])(?=.*\\d)(?=.*[~!@#$%^&*()+|=])[A-Za-z\\d~!@#$%^&*()+|=]{4,20}$"
//            , message = "비밀번호에 영어소문자, 숫자, 특수문자를 모두 포함해주세요")
    private String password;

//    @NotBlank(message = "비밀번호를 입력해주세요.")
//    @Size(min=4,max=32, message= "비밀번호는 최소 4자이상 최대 20자미만으로 만들어주세요.")
//    @Pattern(regexp = "^(?=.*[A-Za-z])(?=.*\\d)(?=.*[~!@#$%^&*()+|=])[A-Za-z\\d~!@#$%^&*()+|=]{4,20}$"
//            , message = "비밀번호에 영어대소문자, 숫자, 특수문자를 모두 포함해주세요")
    private String passwordConfirm;

}
