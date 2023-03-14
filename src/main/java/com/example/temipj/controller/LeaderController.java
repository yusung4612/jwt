package com.example.temipj.controller;

import com.example.temipj.domain.UserDetailsImpl;
import com.example.temipj.dto.responseDto.ResponseDto;
import com.example.temipj.service.LeaderCheckService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class LeaderController {

    private final LeaderCheckService leaderCheckService;

    // 리더 임명
    @PostMapping("/leaders/{employeeId}")
    public ResponseDto<?> LeaderCheck(@PathVariable Long employeeId, @AuthenticationPrincipal UserDetailsImpl userDetails) {
        return leaderCheckService.LeaderCheck(employeeId, userDetails);
    }

    //리더 전체 조회
    @GetMapping("/leaders/all")
    public ResponseDto<?> getLeaderAll() {
        return leaderCheckService.getLeaderAll();
    }


    //리더 생성
//    @PostMapping(value = "/create")
//    private LeadResponseDto<?> create(@RequestBody LeaderRequestDto leaderRequestDto,
//                                      HttpServletRequest request) {
//        return leaderService.createLeader(leaderRequestDto, request);
//    }
//
//    @GetMapping(value = "/all")
//    public LeadResponseDto<?> getLeaderAll(){
//        return leaderService.getLeaderAll();
//    }
}
