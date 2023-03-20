package com.example.temipj.dto.responseDto.Employee;

import com.example.temipj.exception.ErrorCode;
import com.fasterxml.jackson.core.Version;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.aspectj.apache.bcel.classfile.Code;

@Getter
@Setter
@AllArgsConstructor
public class EmpResponseDto<T> {

    private String version;
    private T member;
//    private Error error;

    public static <T> EmpResponseDto<T> version(T data) {
        return new EmpResponseDto<>("1.0.0", data);
    }

//    public static <T> EmpResponseDto<T> fail(String code, String message) {
//        return new EmpResponseDto<>("Error", null);
//    }

    static class Error {
        private String code;
        private String message;
    }
}
