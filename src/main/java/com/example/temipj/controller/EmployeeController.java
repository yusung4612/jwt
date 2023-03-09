package com.example.temipj.controller;

import com.example.temipj.dto.requestDto.EmployeeRequestDto;
import com.example.temipj.dto.responseDto.ResponseDto;
import com.example.temipj.service.EmployeeService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@RequiredArgsConstructor
@RequestMapping("/api/employees")
public class EmployeeController {

    private final EmployeeService employeeService;

    //직원 등록
    @PostMapping(value = "/create")
    private ResponseDto<?> register(@RequestBody EmployeeRequestDto employeeRequestDto,
                                    HttpServletRequest request) {
        return employeeService.createEmp(employeeRequestDto, request);
    }

    // 전체 직원 조회
    @GetMapping(value = "/all")
    public ResponseDto<?> getEmployeeAll(){
        return employeeService.getEmployeeAll();
    }

    // 특정 직원 조회
    @GetMapping(value = "/{id}")
    public ResponseDto<?> getPost(@PathVariable Long id) {
        return employeeService.getEmployee(id);
    }


    // 직원 수정
    @PutMapping(value = "/{id}")
    public ResponseDto<?> updateEmp(@PathVariable Long id,
                                      @RequestBody EmployeeRequestDto employeeRequestDto,
                                      HttpServletRequest request) {
        return employeeService.updateEmp(id, employeeRequestDto, request);
    }

    // 직원 삭제
    @DeleteMapping("delete/{id}")
    public ResponseDto<?>deleteEmp(@PathVariable Long id,HttpServletRequest request){
        return employeeService.deleteEmp(id,request);
    }
}
