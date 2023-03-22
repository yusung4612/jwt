package com.example.temipj.dto.requestDto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class NewsRequestDto {

    private String message; // 뉴스 메세지

    private String author; // 작성자

}
