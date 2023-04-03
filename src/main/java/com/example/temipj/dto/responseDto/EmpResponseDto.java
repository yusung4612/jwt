package com.example.temipj.dto.responseDto;

import lombok.*;

@Builder
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class EmpResponseDto<T> {

    private String version;
    private T member;

    public static <T> EmpResponseDto<T> version(String version,T data) {
        return new EmpResponseDto<>(version, data);
    }

}
