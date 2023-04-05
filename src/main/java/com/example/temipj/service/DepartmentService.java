package com.example.temipj.service;

import com.example.temipj.domain.admin.Admin;
import com.example.temipj.domain.employee.Department;
import com.example.temipj.domain.employee.Division;
import com.example.temipj.dto.requestDto.DepartmentRequestDto;
import com.example.temipj.dto.requestDto.DivisionRequestDto;
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
        // 1토큰 유효성 확인
        if (!tokenProvider.validateToken(request.getHeader("Refresh_Token"))) {
            return ResponseDto.fail(ErrorCode.INVALID_TOKEN.name(), ErrorCode.INVALID_TOKEN.getMessage());
        }
        // 2.등록
        if (requestDto.getDepartment().isEmpty())
            return ResponseDto.fail(ErrorCode.NOT_BLANK_NAME.name(), ErrorCode.NOT_BLANK_NAME.getMessage());

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
            return ResponseDto.fail(ErrorCode.NOT_EXIST_DEPARTMENT.name(), ErrorCode.NOT_EXIST_DEPARTMENT.getMessage());
        }
        return ResponseDto.success(department);
    }

    // 하위부서 수정
    @Transactional
    public ResponseDto<?> updateDepartment(Long id, DepartmentRequestDto requestDto, HttpServletRequest request) {
        // 1.토큰 유효성 확인
        if (!tokenProvider.validateToken(request.getHeader("Refresh_Token"))) {
            return ResponseDto.fail(ErrorCode.INVALID_TOKEN.name(), ErrorCode.INVALID_TOKEN.getMessage());
        }
        // 2.tokenProvider의 SecurityContextHolder에 저장된 Admin 정보 확인
        Admin admin = (Admin) tokenProvider.getAdminFromAuthentication();
        if (null == admin) {
            return ResponseDto.fail(ErrorCode.ADMIN_NOT_FOUND.name(), ErrorCode.ADMIN_NOT_FOUND.getMessage());
        }
        // 3.하위부서 유무 확인
        Department department = isPresentDepartment(id);
        if (null == department) {
            return ResponseDto.fail(ErrorCode.NOT_EXIST_DEPARTMENT.name(), ErrorCode.NOT_EXIST_DEPARTMENT.getMessage());
        }
        // 4.하위부서 수정
        department.update(requestDto);
        return ResponseDto.success(department);
    }

    // 하위부서 삭제
    public ResponseDto<?> deleteDepart(Long departmentId, HttpServletRequest request) {

        // 1.토큰 유효성 확인
        if (!tokenProvider.validateToken(request.getHeader("Refresh_Token"))) {
            return ResponseDto.fail(ErrorCode.INVALID_TOKEN.name(), ErrorCode.INVALID_TOKEN.getMessage());
        }
        // 2.하위부서 유무 확인
        Department department = isPresentDepartment(departmentId);
        if (null == department) {
            return ResponseDto.fail(ErrorCode.NOT_EXIST_DEPARTMENT.name(), ErrorCode.NOT_EXIST_DEPARTMENT.getMessage());
        }
        // 3.tokenProvider의 SecurityContextHolder에 저장된 Admin 정보 확인
        Admin admin = (Admin) tokenProvider.getAdminFromAuthentication();
        if (null == admin) {
            return ResponseDto.fail(ErrorCode.ADMIN_NOT_FOUND.name(), ErrorCode.ADMIN_NOT_FOUND.getMessage());
        }
        // 4.하위부서 삭제
        departmentRepository.delete(department);
        return ResponseDto.success("해당 하위부서가 삭제되었습니다.");
    }

    @Transactional
    public Department isPresentDepartment(Long id) {
        Optional<Department> optionalDepartment = departmentRepository.findById(id);
        return optionalDepartment.orElse(null);
    }

}
