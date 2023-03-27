package com.example.temipj.controller;

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
    @PostMapping("/leaders/choice/{employeeId}")
    public ResponseDto<?> selectLeader(@PathVariable Long employeeId, HttpServletRequest request) {
        return leaderService.LeaderSelect(employeeId, request);
    }

    // 선택한 리더 목록 조회
    @GetMapping("/leaders")
    public ResponseDto<?> getLeaderAll(HttpServletRequest request) {
        return leaderService.getLeaderAll(request);
    }
//    public String getLeaderAll(HttpServletRequest request) throws JSONException {
//        List<Leader> leaders = leaderService.getLeaderAll(request);
//
//        JSONArray leadersArray = new JSONArray();
//
//        for (Leader leader : leaders) {
//            JSONObject leaderObj = new JSONObject();
//            leaderObj.put("department", leader.getEmployee().getDepartment());
//            leaderObj.put("division", leader.getEmployee().getDivision()    );
//
//            JSONArray emailsArray = new JSONArray();
//            String[] emails = leader.getEmployee().getEmail().split(",");
//            for (String email : emails) {
//                emailsArray.put(email.trim());
//            }
//            leaderObj.put("emails", emailsArray);
//
//            leadersArray.put(leaderObj);
//        }
//        return leadersArray.toString();
//    }

    // 리더 검색
    @GetMapping("/search")
    public ResponseDto<?> search(@RequestParam(value = "keyword") String keyword){
        return leaderService.searchLeader(keyword);
    }
}
