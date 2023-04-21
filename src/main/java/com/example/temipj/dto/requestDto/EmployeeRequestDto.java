package com.example.temipj.dto.requestDto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class EmployeeRequestDto {

    @NotBlank(message = "이름을 입력해주세요.")
    @Size(min = 1, max = 10, message = "이름은 최소 1자이상 최대 10자미만으로 입력해주세요.")
    @Pattern(regexp = "[a-zA-Zㄱ-ㅎ가-힣]{1,10}", message = "이름 형식을 확인해 주세요.")
    private String name; // 직원 이름

    @NotBlank(message = "생일을 입력해주세요.")
    @Size(min = 4, max = 4, message = "생일은 4자로 입력해주세요.")
    @Pattern(regexp = "[0-9]*${4}", message = "생일 형식을 확인해 주세요.")
    private String birth; // 직원 생일

    @NotBlank(message = "유선 전화번호를 입력해주세요.")
    @Size(min = 9, max = 11, message = "이름은 최소 9자이상 최대 10자미만으로 입력해주세요.")
    @Pattern(regexp = "^[0-9]{9,11}$", message = "유선 전화번호 형식을 확인해 주세요.")
    private String extension_number; // 유선 전화번호

    @NotBlank(message = "모바일 번호를 입력해주세요.")
    @Size(min = 9, max = 11, message = "이름은 최소 9자이상 최대 10자미만으로 입력해주세요.")
    @Pattern(regexp = "[0-9]*${9,11}", message = "모바일 번호 형식을 확인해 주세요.")
//    @Pattern(regexp = "^(01[016789])(\d{3,4})(\d{4})$", message = "휴대전화번호 형식을 확인해 주세요.")
    private String mobile_number; // 모바일 번호

    @NotBlank(message = "이메일을 입력해주세요.")
    @Size(min=8,max=30, message= "8자리이상 30자리 미만 글자로 email을 만들어주세요")
//    @Pattern(regexp = "^[0-9a-zA-Z]+@[a-zA-Z]+\\.[a-zA-Z]+$" , message = "이메일 형식을 확인해 주세요.")
//    @Pattern(regexp = "^[0-9a-zA-Z]+@everybot\\.net$" , message = "이메일 형식을 확인해 주세요.")
    @Pattern(regexp = "^[0-9a-zA-Z]+@everybot{1}+\\.net{1}$" , message = "이메일 형식을 확인해 주세요.")
    private String email; // 이메일

    //    @NotBlank(message = "상위 부서명을 입력해주세요.")
//    @Size(min = 1, max = 20, message = "최소 1자이상 최대 20자미만으로 입력해주세요.")
//    @Pattern(regexp = "[a-zA-Zㄱ-ㅎ가-힣]*${1,20}", message = "상위 부서명 형식을 확인해 주세요.")
    private String division; // 상위부서

//    @NotBlank(message = "하위부서명을 입력해주세요.")
//    @Size(min = 1, max = 10, message = "최소 1자이상 최대 20자미만으로 입력해주세요.")
//    @Pattern(regexp = "[a-zA-Zㄱ-ㅎ가-힣]*${1,20}", message = "형식을 확인해 주세요.")
//    private String department; // 하위부서
}
