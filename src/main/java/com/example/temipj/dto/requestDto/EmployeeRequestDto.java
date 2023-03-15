package com.example.temipj.dto.requestDto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class EmployeeRequestDto {

//    @NotBlank(message = "이름을 입력해주세요.")
//    @Size(min = 1, max = 10, message = "이름은 최소 1자이상 최대 10자미만으로 입력해주세요.")
//    @Pattern(regexp = "[a-zA-Zㄱ-ㅎ가-힣]*${1,10}", message = "이름 형식을 확인해 주세요.")
    private String name; //직원 이름


    private String birth; //직원 생일


    private String extension_number; //무선전화번호


    private String mobile_number; //모바일번호

//    @NotBlank(message = "이메일을 입력해주세요!")
//    @Size(min=8,max=30, message= "8자리이상 30자리 미만 글자로 email를 만들어주세요")
//    @Pattern(regexp = "^[0-9a-zA-Z]+@[a-zA-Z]+\\.[a-zA-Z]+$" , message = "이메일 형식을 확인해 주세요.")
    private String email; //이메일

//    @NotBlank(message = "팀명을 입력해주세요.")
//    @Size(min = 1, max = 10, message = "최소 1자이상 최대 20자미만으로 입력해주세요.")
//    @Pattern(regexp = "[a-zA-Zㄱ-ㅎ가-힣]*${1,20}", message = "이름 형식을 확인해 주세요.")
    private String division; // 팀 구분

//    @NotBlank(message = "부서명을 입력해주세요.")
//    @Size(min = 1, max = 10, message = "최소 1자이상 최대 20자미만으로 입력해주세요.")
//    @Pattern(regexp = "[a-zA-Zㄱ-ㅎ가-힣]*${1,20}", message = "형식을 확인해 주세요.")
    private String department; // 부서


}
