package com.example.temipj.dto.responseDto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Builder
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class LeaderResponseDto {

    private String division; //구분

    private String department; //부서

    private String name; //이름

    private String mobile_number; //모바일 번호

    private String email; //이메일
}
