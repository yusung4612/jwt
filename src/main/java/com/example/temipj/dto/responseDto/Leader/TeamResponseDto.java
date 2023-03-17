package com.example.temipj.dto.responseDto.Leader;

import com.example.temipj.domain.Employee;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Builder
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class TeamResponseDto {

//    private Long id;

    private String title;

    private List<Employee> employeeResponseDtoList;

}
