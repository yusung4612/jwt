package com.example.temipj.dto.responseDto.Leader;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class LeadResponseDto<T> {

    private String version;
    private T division;

    public static <T> LeadResponseDto<T> version(T division) {
        return new LeadResponseDto("230331", division);
    }

//    @Getter
//    @AllArgsConstructor
//    static class Error {
//        private String code;
//        private String message;
//    }


}
