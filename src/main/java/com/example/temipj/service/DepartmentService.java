package com.example.temipj.service;

import com.example.temipj.domain.admin.Admin;
import com.example.temipj.domain.employee.Department;
import com.example.temipj.domain.employee.Division;
import com.example.temipj.dto.requestDto.DepartmentRequestDto;
import com.example.temipj.dto.responseDto.DepartmentResponseDto;
import com.example.temipj.dto.responseDto.ResponseDto;
import com.example.temipj.exception.CustomException;
import com.example.temipj.exception.ErrorCode;
import com.example.temipj.jwt.TokenProvider;
import com.example.temipj.repository.DepartmentRepository;
import com.example.temipj.repository.DivisionRepository;
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
public class DepartmentService {

    private final TokenProvider tokenProvider;

    private final DepartmentRepository departmentRepository;

    private final DivisionRepository divisionRepository;

    private final DivisionService divisionService;

    // 하위부서 생성
    @Transactional
    public ResponseDto<?> createDepart(String divisionId, DepartmentRequestDto requestDto, HttpServletRequest request) {
        // 1.토큰 유효성 확인
        if (!tokenProvider.validateToken(request.getHeader("Refresh_Token"))) {
            throw new CustomException(ErrorCode.INVALID_TOKEN);
        }
        // 2. 등록
        if (requestDto.getDepartment().isEmpty())
            throw new CustomException(ErrorCode.NOT_BLANK_NAME);

        Division division = divisionRepository.findById(divisionId);

        Department department = Department.builder()
                .department(requestDto.getDepartment())
                .division(division)
                .build();
        departmentRepository.save(department);

        return ResponseDto.success(
                DepartmentResponseDto.builder()
                        .id(department.getId())
                        .department(department.getDepartment())
                        .build());
    }

    // 하위부서 전체 조회
    @Transactional
    public ResponseDto<?> getDepartmentAll() {

        List<Department> departmentList = departmentRepository.findAllByOrderByCreatedAtDesc();
        List<DepartmentResponseDto> departmentResponseDtoList = new ArrayList<>();

        for (Department department : departmentList) {
            departmentResponseDtoList.add(
                    DepartmentResponseDto.builder()
                            .id(department.getId())
                            .department(department.getDepartment())
                            .build());
        }
        return ResponseDto.success(departmentResponseDtoList);
    }

    // 특정 하위부서 조회
    @Transactional
    public ResponseDto<?> getDepartment(Long id) {
        // 하위부서 유무 확인
        Department department = isPresentDepartment(id);
        if (null == department) {
            throw new CustomException(ErrorCode.NOT_EXIST_DEPARTMENT);
        }
        return ResponseDto.success(department);
    }

    // 하위부서 삭제
    public ResponseDto<?> deleteDepart(Long departmentId, HttpServletRequest request) {

        // 1. 토큰 유효성 확인
        if (!tokenProvider.validateToken(request.getHeader("Refresh_Token"))) {
            throw new CustomException(ErrorCode.INVALID_TOKEN);
        }
        // 2. 하위부서 유무 확인
        Department department = isPresentDepartment(departmentId);
        if (null == department) {
            throw new CustomException(ErrorCode.NOT_EXIST_DEPARTMENT);
        }
        // 3. 하위부서 삭제
        departmentRepository.delete(department);
        return ResponseDto.success("해당 하위부서가 삭제되었습니다.");
    }

    @Transactional
    public Department isPresentDepartment(Long id) {
        Optional<Department> optionalDepartment = departmentRepository.findById(id);
        return optionalDepartment.orElse(null);
    }

}
