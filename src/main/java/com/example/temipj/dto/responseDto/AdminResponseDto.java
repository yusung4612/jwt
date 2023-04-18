package com.example.temipj.dto.responseDto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Builder
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class AdminResponseDto {

    private Long id;

    private String adminName; // 이름

    private String emailId; // 이메일

    private LocalDateTime createdAt; // 생성시간

    private LocalDateTime modifiedAt; // 수정시간

    private String accessToken; //accessToken
}
