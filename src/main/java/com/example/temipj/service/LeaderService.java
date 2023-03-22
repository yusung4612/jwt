package com.example.temipj.service;

import com.example.temipj.domain.employee.Employee;
import com.example.temipj.domain.employee.Leader;
import com.example.temipj.domain.member.Member;
import com.example.temipj.dto.responseDto.*;
import com.example.temipj.dto.responseDto.LeadResponseDto;
import com.example.temipj.dto.responseDto.LeaderResponseDto;
import com.example.temipj.exception.CustomException;
import com.example.temipj.exception.ErrorCode;
import com.example.temipj.jwt.TokenProvider;
import com.example.temipj.repository.LeaderRepository;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.*;

@RequiredArgsConstructor
@Service
public class LeaderService {

    private final TokenProvider tokenProvider;
    private final LeaderRepository leaderRepository;
    private final EmployeeService employeeService;

    //리더 선택 및 해제
    @Transactional
    public ResponseDto<?> LeaderSelect(Long employeeId, HttpServletRequest request) {
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
        Leader findLeaderSelect = leaderRepository.findByEmployeeIdAndMemberId(employee.getId(), member.getId());
        if (null != findLeaderSelect) {
            leaderRepository.delete(findLeaderSelect);
            return ResponseDto.success("리더 해제");
        }
        Leader leader = Leader.builder()
                .member(member)
                .employee(employee)
                .build();
        leaderRepository.save(leader);
        return ResponseDto.success("리더 지정");

    }

    // 선택한 리더 목록 조회
    @Transactional
    public LeadResponseDto<?> getLeaderAll(HttpServletRequest request) {
        // 1. 토큰 유효성 확인
        if (!tokenProvider.validateToken(request.getHeader("Refresh_Token"))) {
            throw new CustomException(ErrorCode.INVALID_TOKEN);
        }
        // 2. tokenProvider Class의 SecurityContextHolder에 저장된 Member 정보 확인
        Member member = tokenProvider.getMemberFromAuthentication();
        if (null == member) {
            throw new CustomException(ErrorCode.MEMBER_NOT_FOUND);
        }

        ////////////////////////////////원래/////////////////////

        List<Leader> leaderList = leaderRepository.findAllByMember(member);
        List<LeaderResponseDto> LeaderResponseDtoList = new ArrayList<>();


        for (Leader leader : leaderList) {
            LeaderResponseDtoList.add(
                    LeaderResponseDto.builder()
//                            .id(leader.getEmployee().getId())
//                            .division(leader.getEmployee().getDivision())
                            .department(leader.getEmployee().getDepartment())
                            .name(leader.getEmployee().getName())
                            .mobile_number(leader.getEmployee().getMobile_number())
                            .email(leader.getEmployee().getEmail())
                            .build());
        }
        return LeadResponseDto.version(LeaderResponseDtoList);
    }
////////////////////////////////원래/////////////////////

    //리더 검색
    @Transactional
    public ResponseDto<?> searchLeader(String keyword) {
        List<Employee> leaderList = leaderRepository.searchLead(keyword);
        // 검색된 항목 담아줄 리스트 생성
        List<LeaderResponseDto> LeaderResponseDtoList = new ArrayList<>();
        //for문을 통해서 List에 담아주기
        for (Employee employee : leaderList) {
            LeaderResponseDtoList.add(
                    LeaderResponseDto.builder()
//                            .id(employee.getId())
                            .department(employee.getDepartment())
                            .name(employee.getName())
                            .mobile_number(employee.getMobile_number())
                            .email(employee.getEmail())
                            .build()
            );
        }
        return ResponseDto.success(LeaderResponseDtoList);
    }

}



