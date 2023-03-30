package com.example.temipj.dto.responseDto;

import com.example.temipj.dto.responseDto.EmployeeResponseDto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Builder
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class LeaderResponseDto {

    private String department; // 하위부서

    private String name; // 이름

    private String mobile_number; // 모바일 번호

    private String email; // 이메일

//    private List<EmployeeResponseDto> leaderCheck;

}
