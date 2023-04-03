package com.example.temipj.dto.responseDto;

import com.example.temipj.domain.employee.Division;
import com.example.temipj.domain.employee.Employee;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class TestResponse {
    private String division;
    private String department;
    private String name;
    private String mobile;
    private String email;

    public static TestResponse empOf(Employee e) {
        return TestResponse.builder()
                .division(e.getDepartment().getDivision().getDivision())
                .department(e.getDepartment().getDepartment())
                .name(e.getName())
                .mobile(e.getMobile_number())
                .email(e.getEmail())
                .build();
    }

    public static TestResponse divisionOf(Division d) {
        return TestResponse.builder()
                .division(d.getDivision())
                .build();
    }
}
