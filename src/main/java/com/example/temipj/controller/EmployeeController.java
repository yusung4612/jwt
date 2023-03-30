package com.example.temipj.controller;

import com.example.temipj.domain.UserDetailsImpl;
import com.example.temipj.dto.requestDto.EmployeeRequestDto;
import com.example.temipj.dto.responseDto.EmpResponseDto;
import com.example.temipj.dto.responseDto.EmployeeResponseDto;
import com.example.temipj.dto.responseDto.ResponseDto;
import com.example.temipj.dto.responseDto.TestDto.ResponseFirstDto;
import com.example.temipj.service.EmployeeService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/employees")
public class EmployeeController {

    private final EmployeeService employeeService;

    //직원 생성
//    @PostMapping(value = "/create")
//    private EmpResponseDto<EmployeeResponseDto> create(@RequestBody EmployeeRequestDto employeeRequestDto,
//                                                       HttpServletRequest request) {
//        return employeeService.createEmp(employeeRequestDto, request);
//    }

    //직원 생성 Controller
    @PostMapping(value = "/create/{departmentId}")
    private EmpResponseDto<EmployeeResponseDto> create(@PathVariable String departmentId,
                                                       @RequestBody EmployeeRequestDto employeeRequestDto,
                                                       HttpServletRequest request) {
        return employeeService.createEmp(departmentId , employeeRequestDto, request);
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

    //==========================리더테스트==========================
    // 리더 선택
    @PostMapping("/choice/{id}")
    public ResponseDto<?> selectLeader(@PathVariable Long id) {
        return employeeService.LeaderSelect(id);
    }

    // 선택한 리더 목록 조회
    @GetMapping("/leader/all")
    public ResponseDto<?> getLeaderAll() {
        return employeeService.getLeaderAll();
    }

    //==============================================================

    @GetMapping("/test")
    public ResponseFirstDto test(){
        return employeeService.test("R&D");
    }

}
