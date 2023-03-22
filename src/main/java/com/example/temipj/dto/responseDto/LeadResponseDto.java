package com.example.temipj.dto.responseDto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Builder
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class LeadResponseDto<T> {

    private String version;
    private T division;

    public static <T> LeadResponseDto<T> version(T data) {
        return new LeadResponseDto("230331", data);
    }

//    @Getter
//    @AllArgsConstructor
//    static class Error {
//        private String code;
//        private String message;
//    }


}
