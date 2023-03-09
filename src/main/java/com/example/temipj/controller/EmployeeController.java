package com.example.temipj.controller;

import com.example.temipj.dto.requestDto.EmployeeRequestDto;
import com.example.temipj.dto.responseDto.ResponseDto;
import com.example.temipj.service.EmployeeService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequiredArgsConstructor
@RequestMapping("/api/employees")
public class EmployeeController {

    private final EmployeeService employeeService;

    //직원 등록
    @PostMapping("/register")
    private ResponseDto<?> register(@RequestBody EmployeeRequestDto requestDto,
                                    HttpServletRequest request) {
        return employeeService.register(requestDto, request);
    }

    // 전체 직원 조회

    // 특정 직원 조회

    // 직원 수정

    // 직원 삭제
}
