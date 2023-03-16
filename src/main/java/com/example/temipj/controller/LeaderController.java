package com.example.temipj.controller;

import com.example.temipj.dto.responseDto.Leader.LeadResponseDto;
import com.example.temipj.dto.responseDto.ResponseDto;
import com.example.temipj.service.LeaderService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class LeaderController {

    private final LeaderService leaderService;

    // 리더 지정
    @PostMapping("/leaders/check/{employeeId}")
    public ResponseDto<?> createLeader(@PathVariable Long employeeId, HttpServletRequest request) {
        return leaderService.LeaderCheck(employeeId, request);
    }

    //리더 전체 목록 조회
    @GetMapping("/leaders")
    public LeadResponseDto<?> getLeaderAll(HttpServletRequest request) {
        return leaderService.getLeaderAll(request);
    }
}
