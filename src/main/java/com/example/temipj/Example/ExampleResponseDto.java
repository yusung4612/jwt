package com.example.temipj.Example;

import com.example.temipj.domain.Employee;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Builder
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class ExampleResponseDto {

    private  String email;
    private  String birth;
    private  String name;

    public ExampleResponseDto(Employee employee) {
        this.email = employee.getEmail();
        this.birth = employee.getBirth();
        this.name = employee.getName();
    }


    // 필요한 데이터
        // 필요한 데이터 매핑

    // getter, setter, toString 등 필요한 메소드 추가
}