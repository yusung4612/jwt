package com.example.temipj.dto.requestDto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class DivisionRequestDto {

    //    @NotBlank(message = "상위부서명을 입력해주세요.")
//    @Size(min = 1, max = 10, message = "최소 1자이상 최대 20자미만으로 입력해주세요.")
//    @Pattern(regexp = "[a-zA-Zㄱ-ㅎ가-힣]*${1,20}", message = "이름 형식을 확인해 주세요.")
    private String division;

}
