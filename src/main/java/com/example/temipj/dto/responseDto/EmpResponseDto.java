package com.example.temipj.dto.responseDto;

import lombok.*;

@Builder
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class EmpResponseDto<T> {

    private String version;
    private T member;
//    private Error error;

    public static <T> EmpResponseDto<T> version(T data) {
        return new EmpResponseDto<>("20230331", data);
    }

//    public static <T> EmpResponseDto<T> fail(String code, String message) {
//        return new EmpResponseDto<>("Error", null);
//    }

    static class Error {
        private String code;
        private String message;
    }
}
