package com.example.temipj.dto.responseDto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Builder
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class ChoiceResponseDto<T> {

    private String version;
    private T news;

    public static <T> ChoiceResponseDto<T> version(T data) {
        return new ChoiceResponseDto("230331", data);
    }

}
