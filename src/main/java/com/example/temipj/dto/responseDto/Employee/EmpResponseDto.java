package com.example.temipj.dto.responseDto.Employee;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class EmpResponseDto<T> {

    private String version;
    private T member;
//    private Error error;

    public static <T> EmpResponseDto<T> version(T data) {
//        return new ResponseDto<>(1, data, null);
        return new EmpResponseDto<>("1.0.0", data);
    }

//    public static <T> EmpResponseDto<T> fail(String code, String message) {
//        return new EmpResponseDto<>("Error", null);
//    }

    @Getter
    @AllArgsConstructor
    static class Error {
        private String code;
        private String message;
    }
}
