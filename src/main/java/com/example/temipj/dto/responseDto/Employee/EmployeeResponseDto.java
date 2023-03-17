package com.example.temipj.dto.responseDto.Employee;

import com.example.temipj.domain.Member;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Builder
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class EmployeeResponseDto {

//    private Long id;

    private String name; //직원 이름

    private String birth; //직원 생일

    private String division; // 팀 구분

    private String extension_number; //무선 전화 번호

    private String mobile_number; //모바일번호

    private boolean enabled;

    private Member member;
//    private String email; //이메일
//
//    private String department; // 부서

}