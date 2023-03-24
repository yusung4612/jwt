package com.example.temipj.controller;

import com.example.temipj.domain.UserDetailsImpl;
import com.example.temipj.domain.employee.Employee;
import com.example.temipj.dto.requestDto.EmployeeRequestDto;
import com.example.temipj.dto.responseDto.EmpResponseDto;
import com.example.temipj.dto.responseDto.EmployeeResponseDto;
import com.example.temipj.dto.responseDto.ResponseDto;
import com.example.temipj.service.EmployeeService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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
//    @GetMapping("/all")
//    public String getAll(@AuthenticationPrincipal UserDetailsImpl userDetails) throws JSONException {
//        List<Employee> employees = employeeService.getEmployeeAll(userDetails);
//
//        JSONArray employeeArray = new JSONArray();
//
//        for (Employee employee : employees) {
//            JSONObject employeeObj = new JSONObject();
//            employeeObj.put("name", employee.getName());
//            employeeObj.put("department", employee.getDepartment());
//
//            JSONArray divisionArray = new JSONArray();
//            String[] divisions = employee.getDivision().split(",");
//            for (String language : divisions) {
//                divisionArray.put(language.trim());
//            }
//            employeeObj.put("languages", divisionArray);
//
//            employeeArray.put(employeeObj);
//        }
//        return employeeArray.toString();
//    }

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
