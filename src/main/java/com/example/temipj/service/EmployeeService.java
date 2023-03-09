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

@RequiredArgsConstructor
@Service
public class EmployeeService {

    private final EmployeeRepository employeeRepository;
    private final TokenProvider tokenProvider;

    //직원 등록
    @Transactional
    public ResponseDto<?> register(EmployeeRequestDto requestDto, HttpServletRequest request) {

        if (null == request.getHeader("Refresh-Token")) {
            return ResponseDto.fail(ErrorCode.INVALID_TOKEN.name(), ErrorCode.INVALID_TOKEN.getMessage());
        }
//
//        if (null == request.getHeader("Authorization")) {
//            return ResponseDto.fail(ErrorCode.INVALID_TOKEN.name(), ErrorCode.INVALID_TOKEN.getMessage());
//        }

//        if (!tokenProvider.validateToken(request.getHeader("Refresh_Token"))) {
//            return ResponseDto.fail(ErrorCode.INVALID_TOKEN.name(), ErrorCode.INVALID_TOKEN.getMessage());
//        }

        Member member = (Member) tokenProvider.getMemberFromAuthentication(); // 수정
        if (null == member) {
            return ResponseDto.fail(ErrorCode.MEMBER_NOT_FOUND.name(),ErrorCode.MEMBER_NOT_FOUND.getMessage());
        }

        Employee employee = Employee.builder()
                .name(requestDto.getName())
                .birth(requestDto.getBirth())
                .extension_number(requestDto.getExtension_number())
                .mobile_number(requestDto.getMobile_number())
                .email(requestDto.getEmail())
                .division(requestDto.getDivision())
                .department(requestDto.getDepartment())
                .build();
        employeeRepository.save(employee);

        return ResponseDto.success(
                EmployeeResponseDto.builder()
                        .name(employee.getName())
                        .birth(employee.getBirth())
                        .extension_number(employee.getExtension_number())
                        .mobile_number(employee.getMobile_number())
                        .email(employee.getEmail())
                        .division(employee.getDivision())
                        .department(employee.getDepartment())
                        .build());

    }
}
