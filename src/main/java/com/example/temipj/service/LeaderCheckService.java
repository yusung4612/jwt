package com.example.temipj.service;

import com.example.temipj.domain.Employee;
import com.example.temipj.domain.LeaderCheck;
import com.example.temipj.domain.Member;
import com.example.temipj.domain.UserDetailsImpl;
import com.example.temipj.dto.responseDto.*;
import com.example.temipj.jwt.TokenProvider;
import com.example.temipj.repository.EmployeeRepository;
import com.example.temipj.repository.LeaderCheckRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class LeaderCheckService {

    private final TokenProvider tokenProvider;
    private final EmployeeRepository employeeRepository;
    private final LeaderCheckRepository leaderCheckRepository;
    private final EmployeeService employeeService;

    //리더 체크
    public ResponseDto<?> LeaderCheck(Long id, UserDetailsImpl userDetails) {
        Member member = userDetails.getMember();

        Employee employee = employeeService.isPresentEmployee(id);

        //LeaderCheck DB에서 member와 employeeId가 존재하는지 확인
        //멤버번호, 직원번호 조회
        Optional<LeaderCheck> leaderCheck = leaderCheckRepository.findByMemberAndEmployee(member, employee);
        if (leaderCheck.isPresent()) {
            leaderCheckRepository.deleteById(leaderCheck.get().getId());
            return ResponseDto.success(false);
        } else {
            LeaderCheck leader = LeaderCheck.builder()
                    .employee(employee)
                    .member(member)
                    .build();
            leaderCheckRepository.save(leader);
            return ResponseDto.success(true);
        }
    }

    //리더 목록 조회
    @Transactional
    public ResponseDto<?> getLeaderAll() {

        List<Employee> leaderCheckList = employeeRepository.findAllByOrderByCreatedAtDesc();
        List<LeaderResponseDto> leaderResponseDtoList = new ArrayList<>();

        for (Employee employee : leaderCheckList) {
            leaderResponseDtoList.add(
                    LeaderResponseDto.builder()
                            .name(employee.getName())
                            .division(employee.getDivision())
                            .department(employee.getDepartment())
                            .mobile_number(employee.getMobile_number())
                            .email(employee.getEmail())
                            .build());
        }
        return ResponseDto.success(leaderResponseDtoList);
    }









    // 리더 생성
//    @Transactional
//    public LeadResponseDto createLeader(LeaderRequestDto requestDto, HttpServletRequest request) {
//        // 1. 토큰 유효성 확인
//        if (!tokenProvider.validateToken(request.getHeader("Refresh_Token"))) {
//            throw new CustomException(ErrorCode.INVALID_TOKEN);
//        }
//        // 2. tokenProvider Class의 SecurityContextHolder에 저장된 Member 정보 확인
//        Member member = (Member) tokenProvider.getMemberFromAuthentication();
//        if (null == member) {
//            throw new CustomException(ErrorCode.MEMBER_NOT_FOUND);
//        }
//        // 3. 등록
//        if (requestDto.getName().isEmpty())
//            throw new CustomException(ErrorCode.NOT_BLANK_NAME);
//        Leader leader = Leader.builder()
//                .name(requestDto.getName())
//                .department(requestDto.getDepartment())
//                .telephone(requestDto.getTelephone())
//                .email(requestDto.getEmail())
//                .member(member)
//                .build();
//        leaderRepository.save(leader);
//
//        return LeadResponseDto.version(
//                LeaderResponseDto.builder()
//                        .name(requestDto.getName())
//                        .department(requestDto.getDepartment())
//                        .telephone(requestDto.getTelephone())
//                        .email(requestDto.getEmail())
//                        .build());
//    }
//
//    // 리더 전체 조회
//    @Transactional
//    public LeadResponseDto<?> getLeaderAll() {
//
//        List<Leader> leaderList = leaderRepository.findAllByOrderByCreatedAtDesc();
//        List<LeaderResponseDto> leaderResponseDtoList = new ArrayList<>();
//
//        for (Leader leader : leaderList) {
//           leaderResponseDtoList.add(
//                    LeaderResponseDto.builder()
//                            .name(leader.getName())
//                            .department(leader.getDepartment())
//                            .telephone(leader.getTelephone())
//                            .email(leader.getEmail())
//                            .build());
//        }
//        return LeadResponseDto.version(leaderResponseDtoList);
//    }

}
