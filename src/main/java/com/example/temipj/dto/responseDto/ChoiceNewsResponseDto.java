package com.example.temipj.dto.responseDto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Builder
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class ChoiceNewsResponseDto<T> {

    private String version;
    private T news;

    public static <T> ChoiceNewsResponseDto<T> version(T data) {
        return new ChoiceNewsResponseDto("230331", data);
    }

}
