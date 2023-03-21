package com.example.temipj.Example;

import com.example.temipj.domain.Employee;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ExampleService {

    private final ExampleRepository exampleRepository;

    public ExampleService(ExampleRepository exampleRepository) {
        this.exampleRepository = exampleRepository;
    }

    public List<ExampleResponseDto> getData() {
        List<Employee> dataList = exampleRepository.findAll();

        return dataList.stream()
                .map(ExampleResponseDto::new)
                .collect(Collectors.toList());
    }
}
