package com.example.temipj.domain;

import lombok.Getter;
import java.util.Arrays;

@Getter
public enum Category {

    members("members",0),

    division("division",1),

    development("R&D",1-1),
    sales("영업마케팅",1-2),
    business("경영지원부",1-3),
    manufacturing("제조운영그룹",1-4),

    departement("departement",2),
    contact("contact",3),

    news("news",4);


    private  String value;
    private int code;

    Category(String value, int code) {

        this.value = value;
        this.code = code;
    }

//    public static Category fromCode(String dbData){
//        return Arrays.stream(Category.values())
//                .filter(v -> v.getValue().equals(dbData))
//                .findAny()
//                .orElseThrow(() -> new IllegalArgumentException(String.format("포스트 카테고리에 %s가 존재하지 않습니다.", dbData)));
//    }
}


