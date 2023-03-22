package com.example.temipj.dto.responseDto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Builder
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class SelectsResponseDto<T> {

    private String version;
    private T news;

    public static <T> SelectsResponseDto<T> version(T data) {
        return new SelectsResponseDto("230331", data);
    }

}
