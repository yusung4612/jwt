package com.example.temipj.service;


import com.example.temipj.domain.Employee;
import com.example.temipj.domain.Member;
import com.example.temipj.dto.requestDto.EmployeeRequestDto;
import com.example.temipj.dto.responseDto.EmployeeResponseDto;
import com.example.temipj.dto.responseDto.ResponseDto;
import com.example.temipj.error.ErrorCode;
import com.example.temipj.jwt.TokenProvider;
import com.example.temipj.repository.EmployeeRepository;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@RequiredArgsConstructor
@Service
public class EmployeeService {

    private final EmployeeRepository employeeRepository;
    private final TokenProvider tokenProvider;

    //직원 등록
    @Transactional
    public ResponseDto<?> createEmp(EmployeeRequestDto requestDto, HttpServletRequest request) {

        if (!tokenProvider.validateToken(request.getHeader("Refresh_Token"))) {
            return ResponseDto.fail(ErrorCode.INVALID_TOKEN.name(), ErrorCode.INVALID_TOKEN.getMessage());
        }

        Member member = (Member) tokenProvider.getMemberFromAuthentication();
        if (null == member) {
            return ResponseDto.fail(ErrorCode.MEMBER_NOT_FOUND.name(), ErrorCode.MEMBER_NOT_FOUND.getMessage());
        }

        if (requestDto.getEmpName().isEmpty())
            return ResponseDto.fail(ErrorCode.NOT_BLANK_NAME.name(), ErrorCode.NOT_BLANK_NAME.getMessage());
        Employee employee = Employee.builder()
                .empName(requestDto.getEmpName())
                .birth(requestDto.getBirth())
                .extension_number(requestDto.getExtension_number())
                .mobile_number(requestDto.getMobile_number())
                .email(requestDto.getEmail())
                .division(requestDto.getDivision())
                .department(requestDto.getDepartment())
                .member(member)
                .build();
        employeeRepository.save(employee);

        return ResponseDto.success(
                EmployeeResponseDto.builder()
                        .id(employee.getId())
                        .empName(employee.getEmpName())
                        .birth(employee.getBirth())
                        .extension_number(employee.getExtension_number())
                        .mobile_number(employee.getMobile_number())
                        .email(employee.getEmail())
                        .division(employee.getDivision())
                        .department(employee.getDepartment())
                        .build());
    }

    //전체 직원 조회
    @Transactional
    public ResponseDto<?> getEmployeeAll() {
        return ResponseDto.success(employeeRepository.findAllByOrderByCreatedAtDesc());
    }

    //특정 직원 조회
    @Transactional
    public ResponseDto<?> getEmployee(Long id) {
        Employee employee = isPresentEmployee(id);
        if (null == employee) {
            return ResponseDto.fail(ErrorCode.NOT_EXIST_EMPLOYEE.name(),ErrorCode.NOT_EXIST_EMPLOYEE.getMessage());
        }
        return ResponseDto.success(employee);
    }

    //직원 정보 수정
    public ResponseDto<?> updateEmp(Long id, EmployeeRequestDto requestDto, HttpServletRequest request) {

        if (!tokenProvider.validateToken(request.getHeader("Refresh_Token"))) {
            return ResponseDto.fail(ErrorCode.INVALID_TOKEN.name(), ErrorCode.INVALID_TOKEN.getMessage());
        }

        Employee employee = isPresentEmployee(id);
        if (null == employee) {
            return ResponseDto.fail(ErrorCode.NOT_EXIST_EMPLOYEE.name(), ErrorCode.NOT_EXIST_EMPLOYEE.getMessage());
        }

        Member member = validateMember(request);
        if (null == member) {
            return ResponseDto.fail(ErrorCode.INVALID_TOKEN.name(), ErrorCode.INVALID_TOKEN.getMessage());
        }

//        Member member = (Member) tokenProvider.getMemberFromAuthentication();
//        if (employee.validateMember(member)) {
//        if (employee.validateMember(member)) {
//            return ResponseDto.fail(ErrorCode.EMPLOYEE_UPDATE_WRONG_ACCESS.name(), ErrorCode.EMPLOYEE_UPDATE_WRONG_ACCESS.getMessage());
//        }
        employee.update(requestDto);
        return ResponseDto.success(employee);

//        return ResponseDto.success(
//                EmployeeResponseDto.builder()
//                        .id(employee.getId())
//                        .empName(employee.getEmpName())
//                        .birth(employee.getBirth())
//                        .extension_number(employee.getExtension_number())
//                        .mobile_number(employee.getMobile_number())
//                        .email(employee.getEmail())
//                        .division(employee.getDivision())
//                        .department(employee.getDepartment())
//                        .build());
    }

    //직원 삭제
    public ResponseDto<?> deleteEmp(Long id, HttpServletRequest request) {

        if (!tokenProvider.validateToken(request.getHeader("Refresh_Token"))) {
            return ResponseDto.fail(ErrorCode.INVALID_TOKEN.name(), ErrorCode.INVALID_TOKEN.getMessage());
        }

        Employee employee = isPresentEmployee(id);
        if (null == employee) {
            return ResponseDto.fail(ErrorCode.NOT_EXIST_EMPLOYEE.name(), ErrorCode.NOT_EXIST_EMPLOYEE.getMessage());
        }

        Member member = (Member) tokenProvider.getMemberFromAuthentication();
        if (employee.validateMember(member)) {
            return ResponseDto.fail(ErrorCode.EMPLOYEE_UPDATE_WRONG_ACCESS.name(), ErrorCode.EMPLOYEE_UPDATE_WRONG_ACCESS.getMessage());
        }
        employeeRepository.delete(employee);
        return ResponseDto.success("해당 직원이 삭제되었습니다.");
    }


    @Transactional
    public Employee isPresentEmployee(Long id) {
        Optional<Employee> optionalEmployee = employeeRepository.findById(id);
        return optionalEmployee.orElse(null);
    }

    @Transactional
    public Member validateMember(HttpServletRequest request) {
        if (!tokenProvider.validateToken(request.getHeader("Refresh-Token"))) {
            return null;
        }
        return tokenProvider.getMemberFromAuthentication();
    }
}
