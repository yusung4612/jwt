package com.example.temipj.controller;

import com.example.temipj.domain.UserDetailsImpl;
import com.example.temipj.dto.requestDto.EmployeeRequestDto;
import com.example.temipj.dto.responseDto.EmpResponseDto;
import com.example.temipj.dto.responseDto.EmployeeResponseDto;
import com.example.temipj.dto.responseDto.ResponseDto;
import com.example.temipj.service.EmployeeService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/employees")
public class EmployeeController {

    private final EmployeeService employeeService;

    //직원 생성
    @PostMapping(value = "/create")
    private EmpResponseDto<EmployeeResponseDto> create(@RequestBody EmployeeRequestDto employeeRequestDto,
                                                         HttpServletRequest request) {
        return employeeService.createEmp(employeeRequestDto, request);
    }

    // 전체 직원 조회
    @GetMapping(value = "/all")
    public EmpResponseDto<?> getAll(@AuthenticationPrincipal UserDetailsImpl userDetails){
        return employeeService.getEmployeeAll(userDetails);
    }

    // 특정 직원 조회
    @GetMapping(value = "/{id}")
    public EmpResponseDto<?> get(@PathVariable Long id) {
        return employeeService.getEmployee(id);
    }

    // 직원 수정
    @PutMapping(value = "/{id}")
    public EmpResponseDto<?> update(@PathVariable Long id,
                                       @RequestBody EmployeeRequestDto employeeRequestDto,
                                       HttpServletRequest request) {
        return employeeService.updateEmp(id, employeeRequestDto, request);
    }

    // 직원 삭제
    @DeleteMapping("delete/{id}")
    public EmpResponseDto<?>delete(@PathVariable Long id,HttpServletRequest request){
        return employeeService.deleteEmp(id,request);
    }

    // 직원 검색
    @GetMapping("/search")
    public ResponseDto<?> search(@RequestParam(value = "keyword") String keyword, @AuthenticationPrincipal UserDetailsImpl userDetails){
        return employeeService.searchEmployee(keyword, userDetails);
    }
}
