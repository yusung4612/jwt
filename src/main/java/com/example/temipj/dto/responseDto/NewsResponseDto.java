package com.example.temipj.dto.responseDto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Builder
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class NewsResponseDto {

    private Long id;
    private String message;

    private String author;

    private LocalDate end_date;

    private String choiceNews;

}
