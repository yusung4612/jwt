package com.example.temipj.service;

import com.example.temipj.domain.Employee;
import com.example.temipj.domain.Leader;
import com.example.temipj.domain.Member;
import com.example.temipj.dto.responseDto.*;
import com.example.temipj.exception.CustomException;
import com.example.temipj.exception.ErrorCode;
import com.example.temipj.jwt.TokenProvider;
import com.example.temipj.repository.LeaderRepository;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
@Service
public class LeaderService {

    private final TokenProvider tokenProvider;
    private final LeaderRepository leaderRepository;
    private final EmployeeService employeeService;

    //리더 지정 및 해제
    @Transactional
    public ResponseDto<?> LeaderCheck(Long employeeId, HttpServletRequest request) {
        // 1. 토큰 유효성 확인
        if (!tokenProvider.validateToken(request.getHeader("Refresh_Token"))) {
            throw new CustomException(ErrorCode.INVALID_TOKEN);
        }
        // 2. tokenProvider Class의 SecurityContextHolder에 저장된 Member 정보 확인
        Member member = tokenProvider.getMemberFromAuthentication();
        if (null == member) {
            throw new CustomException(ErrorCode.MEMBER_NOT_FOUND);
        }
        // 3. 직원 확인
        Employee employee = employeeService.isPresentEmployee(employeeId);
        if (null == employee) {
            throw new CustomException(ErrorCode.NOT_EXIST_EMPLOYEE);
        }

        // 4. 리더 체크 저장
        Leader findLeaderCheck = leaderRepository.findByEmployeeIdAndMemberId(employee.getId(), member.getId());
        if (null != findLeaderCheck) {
            leaderRepository.delete(findLeaderCheck);
            return ResponseDto.success("리더 해제");
        }
        Leader leader = Leader.builder()
                .member(member)
                .employee(employee)
                .build();
        leaderRepository.save(leader);
        return ResponseDto.success("리더 지정");

    }

    //리더 목록 조회
    @Transactional
    public ResponseDto<?> getLeaderAll(HttpServletRequest request) {
        // 1. 토큰 유효성 확인
        if (!tokenProvider.validateToken(request.getHeader("Refresh_Token"))) {
            throw new CustomException(ErrorCode.INVALID_TOKEN);
        }
        // 2. tokenProvider Class의 SecurityContextHolder에 저장된 Member 정보 확인
        Member member = tokenProvider.getMemberFromAuthentication();
        if (null == member) {
            throw new CustomException(ErrorCode.MEMBER_NOT_FOUND);
        }

        List<Leader> leaderList = leaderRepository.findAllByMember(member);
        List<LeaderResponseDto> LeaderResponseDtoList = new ArrayList<>();

        for (Leader leader : leaderList) {
            LeaderResponseDtoList.add(
                    LeaderResponseDto.builder()
//                            .id(leader.getEmployee().getId())
                            .division(leader.getEmployee().getDivision())
                            .department(leader.getEmployee().getDepartment())
                            .name(leader.getEmployee().getName())
                            .mobile_number(leader.getEmployee().getMobile_number())
                            .email(leader.getEmployee().getEmail())
                            .build());
        }
        return ResponseDto.success(LeaderResponseDtoList);
    }
}


