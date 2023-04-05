package com.example.temipj.service;

import com.example.temipj.domain.admin.Admin;
import com.example.temipj.domain.employee.Department;
import com.example.temipj.domain.employee.Division;
import com.example.temipj.domain.employee.Employee;
import com.example.temipj.dto.requestDto.DivisionRequestDto;
import com.example.temipj.dto.requestDto.EmployeeRequestDto;
import com.example.temipj.dto.responseDto.DivisionResponseDto;
import com.example.temipj.dto.responseDto.EmpResponseDto;
import com.example.temipj.dto.responseDto.EmployeeResponseDto;
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
public class DivisionService {

    private final TokenProvider tokenProvider;

    private final DivisionRepository divisionRepository;

    // 상위부서(division) 생성
    @Transactional
    public ResponseDto<?> createDivision(DivisionRequestDto requestDto, HttpServletRequest request) {
        // 1. 토큰 유효성 확인
        if (!tokenProvider.validateToken(request.getHeader("Refresh_Token"))) {
            return ResponseDto.fail(ErrorCode.INVALID_TOKEN.name(), ErrorCode.INVALID_TOKEN.getMessage());
        }
        // 2. tokenProvider의 SecurityContextHolder에 저장된 Admin 정보 확인
//        Admin admin = (Admin) tokenProvider.getAdminFromAuthentication();
//        if (null == admin) {
//            return ResponseDto.fail(ErrorCode.ADMIN_NOT_FOUND.name(), ErrorCode.ADMIN_NOT_FOUND.getMessage());
//        }
        // 3. 등록
        if (requestDto.getDivision().isEmpty())
            return ResponseDto.fail(ErrorCode.NOT_BLANK_NAME.name(), ErrorCode.NOT_BLANK_NAME.getMessage());

        Division division = Division.builder()
                .division(requestDto.getDivision())
                .build();
        divisionRepository.save(division);

        return ResponseDto.success(
                DivisionResponseDto.builder()
                        .id(division.getId())
                        .division(division.getDivision())
                        .build());
    }

    // 상위부서 전체 조회
    @Transactional
    public ResponseDto<?> getDivisionAll() {

        List<Division> divisionList = divisionRepository.findAllByOrderByCreatedAtDesc();
        List<DivisionResponseDto> divisionResponseDtoList = new ArrayList<>();

        for (Division division : divisionList) {
            divisionResponseDtoList.add(
                    DivisionResponseDto.builder()
                            .id(division.getId())
                            .division(division.getDivision())
                            .build());
        }
        return ResponseDto.success(divisionResponseDtoList);
    }

    // 특정 상위부서 조회
    @Transactional
    public ResponseDto<?> getDivision(Long id) {
        // 상위부서 유무 확인
        Division division = isPresentDivision(id);
        if (null == division) {
            return ResponseDto.fail(ErrorCode.NOT_EXIST_DIVISION.name(), ErrorCode.NOT_EXIST_DIVISION.getMessage());
        }
        return ResponseDto.success(division);
    }

    // 상위부서 수정
    @Transactional
    public ResponseDto<?> updateDivision(Long id, DivisionRequestDto requestDto, HttpServletRequest request) {
        // 1. 토큰 유효성 확인
        if (!tokenProvider.validateToken(request.getHeader("Refresh_Token"))) {
            return ResponseDto.fail(ErrorCode.INVALID_TOKEN.name(), ErrorCode.INVALID_TOKEN.getMessage());
        }
        // 2. 상위부서 유무 확인
        Division division = isPresentDivision(id);
        if (null == division) {
            return ResponseDto.fail(ErrorCode.NOT_EXIST_DIVISION.name(), ErrorCode.NOT_EXIST_DIVISION.getMessage());
        }
        // 3. tokenProvider Class의 SecurityContextHolder에 저장된 Admin 정보 확인
//        Admin admin = (Admin) tokenProvider.getAdminFromAuthentication();
//        if (null == admin) {
//            return ResponseDto.fail(ErrorCode.ADMIN_NOT_FOUND.name(), ErrorCode.ADMIN_NOT_FOUND.getMessage());
//        }
        // 4. 수정
        division.update(requestDto);
        return ResponseDto.success(division);
    }

    // 상위부서 삭제
    public ResponseDto<?> deleteDivision(Long id, HttpServletRequest request) {

        // 1. 토큰 유효성 확인
        if (!tokenProvider.validateToken(request.getHeader("Refresh_Token"))) {
            return ResponseDto.fail(ErrorCode.INVALID_TOKEN.name(), ErrorCode.INVALID_TOKEN.getMessage());
        }
        // 2. 상위부서 유무 확인
        Division division = isPresentDivision(id);
        if (null == division) {
            return ResponseDto.fail(ErrorCode.NOT_EXIST_DIVISION.name(), ErrorCode.NOT_EXIST_DIVISION.getMessage());
        }
        // 3. tokenProvider의 SecurityContextHolder에 저장된 Admin 정보 확인
//        Admin admin = (Admin) tokenProvider.getAdminFromAuthentication();
//        if (null == admin) {
//            return ResponseDto.fail(ErrorCode.ADMIN_NOT_FOUND.name(), ErrorCode.ADMIN_NOT_FOUND.getMessage());
//        }
        // 4. 상위부서 삭제
        divisionRepository.delete(division);
        return ResponseDto.success("해당 상위부서가 삭제되었습니다.");
    }

    @Transactional
    public Division isPresentDivision(Long id) {
        Optional<Division> optionalDivision = divisionRepository.findById(id);
        return optionalDivision.orElse(null);
    }
}
