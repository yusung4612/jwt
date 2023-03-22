package com.example.temipj.controller;

import com.example.temipj.dto.responseDto.LeadResponseDto;
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

    // 리더 선택
    @PostMapping("/leaders/select/{employeeId}")
    public ResponseDto<?> selectLeader(@PathVariable Long employeeId, HttpServletRequest request) {
        return leaderService.LeaderSelect(employeeId, request);
    }

    // 선택한 리더 목록 조회
    @GetMapping("/leaders")
    public LeadResponseDto<?> getLeaderAll(HttpServletRequest request) {
        return leaderService.getLeaderAll(request);
    }

    // 리더 검색
    @GetMapping("/search")
    public ResponseDto<?> search(@RequestParam(value = "keyword") String keyword){
        return leaderService.searchLeader(keyword);
    }
}
