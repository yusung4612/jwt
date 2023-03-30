package com.example.temipj.service;

import com.example.temipj.domain.admin.Admin;
import com.example.temipj.domain.employee.Department;
import com.example.temipj.domain.employee.Division;
import com.example.temipj.domain.employee.Employee;
import com.example.temipj.dto.requestDto.DivisionRequestDto;
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
            throw new CustomException(ErrorCode.INVALID_TOKEN);
        }
        // 2. tokenProvider Class의 SecurityContextHolder에 저장된 Member 정보 확인
        Admin admin = (Admin) tokenProvider.getAdminFromAuthentication();
        if (null == admin) {
            throw new CustomException(ErrorCode.ADMIN_NOT_FOUND);
        }
        // 3. 등록
        if (requestDto.getDivision().isEmpty())
            throw new CustomException(ErrorCode.NOT_BLANK_NAME);

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
            throw new CustomException(ErrorCode.NOT_EXIST_DIVISION);
        }
        return ResponseDto.success(division);
    }


    // 상위부서 삭제
    public ResponseDto<?> deleteDivision(Long id, HttpServletRequest request) {

        // 1. 토큰 유효성 확인
        if (!tokenProvider.validateToken(request.getHeader("Refresh_Token"))) {
            throw new CustomException(ErrorCode.INVALID_TOKEN);
        }
        // 2. 상위부서 유무 확인
        Division division = isPresentDivision(id);
        if (null == division) {
            throw new CustomException(ErrorCode.NOT_EXIST_DIVISION);
        }
        // 3. SecurityContextHolder에 저장된 Admin 확인
        Admin admin = (Admin) tokenProvider.getAdminFromAuthentication();
//        if (division.validateAdmin(admin)) {
////            return ResponseDto.fail(ErrorCode.EMPLOYEE_UPDATE_WRONG_ACCESS.name(), ErrorCode.EMPLOYEE_UPDATE_WRONG_ACCESS.getMessage());
//            throw new CustomException(ErrorCode.EMPLOYEE_UPDATE_WRONG_ACCESS);
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
