package com.example.temipj.dto.responseDto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class ResponseDto<T> {

//    private boolean success;
//    private T data;
    private Integer version;
    private T member;
//    private Error error;

    public static <T> ResponseDto<T> version(T data) {
//        return new ResponseDto<>(1, data, null);
        return new ResponseDto<>(1, data);
    }

    public static <T> ResponseDto<T> fail(String code, String message) {
        return new ResponseDto<>(2, null);
    }

    @Getter
    @AllArgsConstructor
    static class Error {
        private String code;
        private String message;
    }

}