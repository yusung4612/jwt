package com.example.temipj.dto.responseDto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
@Getter
@AllArgsConstructor
@Builder
public class NewsCrawlDto {
    private String title;
    private String url;
    private String author;

}
