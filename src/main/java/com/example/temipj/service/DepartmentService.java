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

    // 부서 생성
    @Transactional
    public ResponseDto<?> createDepart(String divisionId, DepartmentRequestDto requestDto, HttpServletRequest request) {
        // 1. 토큰 유효성 확인
        if (!tokenProvider.validateToken(request.getHeader("Refresh_Token"))) {
//            return ResponseDto.fail(ErrorCode.INVALID_TOKEN.name(), ErrorCode.INVALID_TOKEN.getMessage());
            throw new CustomException(ErrorCode.INVALID_TOKEN);
        }
        // 2. tokenProvider Class의 SecurityContextHolder에 저장된 Admin 정보 확인
        Admin admin = (Admin) tokenProvider.getAdminFromAuthentication();
        if (null == admin) {
//            return ResponseDto.fail(ErrorCode.ADMIN_NOT_FOUND.name(), ErrorCode.ADMIN_NOT_FOUND.getMessage());
            throw new CustomException(ErrorCode.ADMIN_NOT_FOUND);
        }
        // 3. 등록
        if (requestDto.getDepartment().isEmpty())
//            return ResponseDto.fail(ErrorCode.NOT_BLANK_NAME.name(), ErrorCode.NOT_BLANK_NAME.getMessage());
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

    // 부서 전체 조회
    @Transactional
    public ResponseDto<?> getDepartmentAll() {

        List<Department> departmentList = departmentRepository.findAllByOrderByCreatedAtDesc();
        List<DepartmentResponseDto> departmentResponseDtoList = new ArrayList<>();

        for (Department department : departmentList) {
            departmentResponseDtoList.add(
                    DepartmentResponseDto.builder()
                            .department(department.getDepartment())
                            .build());
        }
        return ResponseDto.success(departmentResponseDtoList);
    }

    // 부서 삭제
    public ResponseDto<?> deleteDepart(Long id, HttpServletRequest request) {

        // 1. 토큰 유효성 확인
        if (!tokenProvider.validateToken(request.getHeader("Refresh_Token"))) {
//            return ResponseDto.fail(ErrorCode.INVALID_TOKEN.name(), ErrorCode.INVALID_TOKEN.getMessage());
            throw new CustomException(ErrorCode.INVALID_TOKEN);
        }
        // 2. 부서 유무 확인
        Department department = isPresentDepartment(id);
        if (null == department) {
//            return ResponseDto.fail(ErrorCode.NOT_EXIST_EMPLOYEE.name(), ErrorCode.NOT_EXIST_EMPLOYEE.getMessage());
            throw new CustomException(ErrorCode.NOT_EXIST_EMPLOYEE);
        }
        // 3. SecurityContextHolder에 저장된 Admin 확인
        Admin admin = (Admin) tokenProvider.getAdminFromAuthentication();
//        if (department.validateAdmin(admin)) {
////            return ResponseDto.fail(ErrorCode.EMPLOYEE_UPDATE_WRONG_ACCESS.name(), ErrorCode.EMPLOYEE_UPDATE_WRONG_ACCESS.getMessage());
//            throw new CustomException(ErrorCode.EMPLOYEE_UPDATE_WRONG_ACCESS);
//        }
        // 4. 부서 삭제
        departmentRepository.delete(department);
//        return ResponseDto.version("해당 직원이 삭제되었습니다.");
        return ResponseDto.success("해당 부서가 삭제되었습니다.");
    }

    @Transactional
    public Department isPresentDepartment(Long id) {
        Optional<Department> optionalDepartment = departmentRepository.findById(id);
        return optionalDepartment.orElse(null);
    }

}
