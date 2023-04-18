package com.example.temipj.dto.responseDto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Builder
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class EmpListDto {

//    private Long id;

    private String name; // 직원 이름

    private String birth; // 직원 생일

    private String division; // 상위부서

    private String extension_number; // 무선 전화 번호

    private String mobile_number; // 모바일번호

    private String email;

    private String department; // 하위부서

    private String enabled;

}