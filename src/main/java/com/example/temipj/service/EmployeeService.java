package com.example.temipj.service;


import com.example.temipj.domain.Employee;
import com.example.temipj.domain.Member;
import com.example.temipj.dto.requestDto.EmployeeRequestDto;
import com.example.temipj.dto.responseDto.EmpResponseDto;
import com.example.temipj.dto.responseDto.EmployeeResponseDto;
import com.example.temipj.dto.responseDto.ResponseDto;
import com.example.temipj.exception.CustomException;
import com.example.temipj.exception.ErrorCode;
import com.example.temipj.jwt.TokenProvider;
import com.example.temipj.repository.EmployeeRepository;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.transaction.Transactional;
import lombok.Builder;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Builder
@RequiredArgsConstructor
@Service
public class EmployeeService {

    private final EmployeeRepository employeeRepository;
    private final TokenProvider tokenProvider;

    //직원 등록
    @Transactional
    public EmpResponseDto<EmployeeResponseDto> createEmp(EmployeeRequestDto requestDto, HttpServletRequest request) {
        // 1. 토큰 유효성 확인
        if (!tokenProvider.validateToken(request.getHeader("Refresh_Token"))) {
//            return ResponseDto.fail(ErrorCode.INVALID_TOKEN.name(), ErrorCode.INVALID_TOKEN.getMessage());
            throw new CustomException(ErrorCode.INVALID_TOKEN);
        }
        // 2. tokenProvider Class의 SecurityContextHolder에 저장된 Member 정보 확인
        Member member = (Member) tokenProvider.getMemberFromAuthentication();
        if (null == member) {
//            return ResponseDto.fail(ErrorCode.MEMBER_NOT_FOUND.name(), ErrorCode.MEMBER_NOT_FOUND.getMessage());
            throw new CustomException(ErrorCode.MEMBER_NOT_FOUND);
        }
        // 3. 등록
        if (requestDto.getEmpName().isEmpty())
//            return ResponseDto.fail(ErrorCode.NOT_BLANK_NAME.name(), ErrorCode.NOT_BLANK_NAME.getMessage());
            throw new CustomException(ErrorCode.NOT_BLANK_NAME);
        Employee employee = Employee.builder()
                .empName(requestDto.getEmpName())
                .birth(requestDto.getBirth())
                .division(requestDto.getDivision())
                .extension_number(requestDto.getExtension_number())
                .mobile_number(requestDto.getMobile_number())
//                .email(requestDto.getEmail())
//                .department(requestDto.getDepartment())
                .member(member)
                .build();
        employeeRepository.save(employee);

        return EmpResponseDto.version(
                EmployeeResponseDto.builder()
//                        .id(employee.getId())
                        .empName(requestDto.getEmpName())
                        .birth(requestDto.getBirth())
                        .division(requestDto.getDivision())
                        .extension_number(requestDto.getExtension_number())
                        .mobile_number(requestDto.getMobile_number())
//                        .email(employee.getEmail())
//                        .department(employee.getDepartment())
                        .build());
    }

    //전체 직원 조회
    @Transactional
    public EmpResponseDto<?> getEmployeeAll() {

        List<Employee> employeeList = employeeRepository.findAllByOrderByCreatedAtDesc();
        List<EmployeeResponseDto> employeeResponseDtoList = new ArrayList<>();

        for (Employee employee : employeeList) {
            employeeResponseDtoList.add(
                    EmployeeResponseDto.builder()
                            .empName(employee.getEmpName())
                            .birth(employee.getBirth())
                            .division(employee.getDivision())
                            .extension_number(employee.getExtension_number())
                            .mobile_number(employee.getMobile_number())
                            .build());
        }
        return EmpResponseDto.version(employeeResponseDtoList);
    }

    //특정 직원 조회
    @Transactional
    public EmpResponseDto<?> getEmployee(Long id) {
        //직원 유무 확인
        Employee employee = isPresentEmployee(id);
        if (null == employee) {
//            return ResponseDto.fail(ErrorCode.NOT_EXIST_EMPLOYEE.name(),ErrorCode.NOT_EXIST_EMPLOYEE.getMessage());
            throw new CustomException(ErrorCode.NOT_EXIST_EMPLOYEE);
        }
        return EmpResponseDto.version(employee);
    }

//    public ResponseDto<?> getEmployee(Long id) {
//        //직원 유무 확인
//        Employee employee = isPresentEmployee(id);
//        if (null == employee) {
////            return ResponseDto.fail(ErrorCode.NOT_EXIST_EMPLOYEE.name(),ErrorCode.NOT_EXIST_EMPLOYEE.getMessage());
//            throw new CustomException(ErrorCode.NOT_EXIST_EMPLOYEE);
//        }
//        return ResponseDto.version(employee);
//    }

    //직원 정보 수정
    @Transactional
//    public ResponseDto<?> updateEmp(Long id, EmployeeRequestDto requestDto, HttpServletRequest request) {
    public EmpResponseDto<?> updateEmp(Long id, EmployeeRequestDto requestDto, HttpServletRequest request) {
        // 1. 토큰 유효성 확인
        if (!tokenProvider.validateToken(request.getHeader("Refresh_Token"))) {
//            return ResponseDto.fail(ErrorCode.INVALID_TOKEN.name(), ErrorCode.INVALID_TOKEN.getMessage());
            throw new CustomException(ErrorCode.INVALID_TOKEN);
        }

        // 2. 직원 유무 확인
        Employee employee = isPresentEmployee(id);
        if (null == employee) {
//            return ResponseDto.fail(ErrorCode.NOT_EXIST_EMPLOYEE.name(), ErrorCode.NOT_EXIST_EMPLOYEE.getMessage());
            throw new CustomException(ErrorCode.NOT_EXIST_EMPLOYEE);
        }
        // 3. tokenProvider Class의 SecurityContextHolder에 저장된 Member 정보 확인
        Member member = (Member) tokenProvider.getMemberFromAuthentication();
        if (employee.validateMember(member)) {
//            return ResponseDto.fail(ErrorCode.EMPLOYEE_UPDATE_WRONG_ACCESS.name(), ErrorCode.EMPLOYEE_UPDATE_WRONG_ACCESS.getMessage());
            throw new CustomException(ErrorCode.EMPLOYEE_UPDATE_WRONG_ACCESS);
        }
        // 4. 수정
        employee.update(requestDto);
//        return ResponseDto.version(employee);
        return EmpResponseDto.version(employee);
    }

    //직원 삭제
//    public ResponseDto<?> deleteEmp(Long id, HttpServletRequest request) {
    public EmpResponseDto<?> deleteEmp(Long id, HttpServletRequest request) {

        // 1. 토큰 유효성 확인
        if (!tokenProvider.validateToken(request.getHeader("Refresh_Token"))) {
//            return ResponseDto.fail(ErrorCode.INVALID_TOKEN.name(), ErrorCode.INVALID_TOKEN.getMessage());
            throw new CustomException(ErrorCode.INVALID_TOKEN);
        }
        // 2. 직원 유무 확인
        Employee employee = isPresentEmployee(id);
        if (null == employee) {
//            return ResponseDto.fail(ErrorCode.NOT_EXIST_EMPLOYEE.name(), ErrorCode.NOT_EXIST_EMPLOYEE.getMessage());
            throw new CustomException(ErrorCode.NOT_EXIST_EMPLOYEE);
        }
        // 3. SecurityContextHolder에 저장된 Member 확인
        Member member = (Member) tokenProvider.getMemberFromAuthentication();
        if (employee.validateMember(member)) {
//            return ResponseDto.fail(ErrorCode.EMPLOYEE_UPDATE_WRONG_ACCESS.name(), ErrorCode.EMPLOYEE_UPDATE_WRONG_ACCESS.getMessage());
            throw new CustomException(ErrorCode.EMPLOYEE_UPDATE_WRONG_ACCESS);
        }
        // 4. 삭제
        employeeRepository.delete(employee);
//        return ResponseDto.version("해당 직원이 삭제되었습니다.");
        return EmpResponseDto.version("해당 직원이 삭제되었습니다.");
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
