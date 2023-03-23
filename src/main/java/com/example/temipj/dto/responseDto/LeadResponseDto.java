package com.example.temipj.dto.responseDto;

import com.example.temipj.domain.employee.Employee;
import com.example.temipj.repository.EmployeeRepository;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.json.JSONObject;

import java.util.LinkedHashMap;
import java.util.Map;

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

}
