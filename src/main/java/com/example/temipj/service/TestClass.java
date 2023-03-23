package com.example.temipj.service;

import com.example.temipj.domain.employee.Employee;
import com.example.temipj.repository.EmployeeRepository;
import jakarta.transaction.Transactional;
import lombok.Builder;
import lombok.RequiredArgsConstructor;
import org.json.JSONObject;
import org.springframework.stereotype.Service;

import java.util.LinkedHashMap;
import java.util.Map;

@Service
@Builder
@RequiredArgsConstructor
public class TestClass {

    private final EmployeeRepository employeeRepository;

    @Transactional
    public JSONObject getResult(){

        String version = "20230323";

        Map<String, String> contact = new LinkedHashMap<>();
        Employee employee = employeeRepository.findByLeader();
        contact.put("name", employee.getName());
        contact.put("mobile_number", employee.getMobile_number());
        contact.put("email", employee.getEmail());

        JSONObject list = new JSONObject();
        list.put("department", employee.getDepartment());
        list.put("contact", contact);

        return list;
    }

}
