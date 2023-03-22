package com.example.temipj.example;

import com.example.temipj.domain.employee.Employee;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;
@RequiredArgsConstructor
@Service
public class ExampleService {

    private final ExampleRepository exampleRepository;

    public List<ExampleResponseDto> getData() {
        List<Employee> employeeList = exampleRepository.findAll();

        return employeeList.stream()
                .map(ExampleResponseDto::new)
                .collect(Collectors.toList());
    }
}
