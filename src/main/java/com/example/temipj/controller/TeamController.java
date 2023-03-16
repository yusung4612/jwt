package com.example.temipj.controller;

import com.example.temipj.dto.requestDto.EmployeeRequestDto;
import com.example.temipj.dto.requestDto.TeamRequestDto;
import com.example.temipj.dto.responseDto.Employee.EmpResponseDto;
import com.example.temipj.dto.responseDto.Employee.EmployeeResponseDto;
import com.example.temipj.dto.responseDto.ResponseDto;
import com.example.temipj.service.TeamService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/teams")
public class TeamController {

    private final TeamService teamService;

    //팀 생성
    @PostMapping(value = "/create")
    private ResponseDto<?> create(@RequestBody TeamRequestDto teamRequestDto,
                                                    HttpServletRequest request) {
        return teamService.createTeam(teamRequestDto, request);
    }

    //특정 팀 조회
    @GetMapping(value = "/{id}")
    public ResponseDto<?> getTeam(@PathVariable Long id) {
        System.out.println("아이디값"+id);
        return teamService.getTeam(id);
    }
    //전체 팀 목록 조회
    @GetMapping(value = "/all")
    public ResponseDto<?> getTeamAll(){
        return teamService.getTeamAll();
    }

    // 팀 수정
    @PutMapping(value = "/update/{id}")
    public ResponseDto<?> updateTeam(@PathVariable Long id,
                                      @RequestBody TeamRequestDto teamRequestDto,
                                      HttpServletRequest request) {
        return teamService.updateTeam(id, teamRequestDto, request);
    }
    // 팀 삭제
    @DeleteMapping("/delete/{id}")
    public ResponseDto<?>deleteTeam(@PathVariable Long id,HttpServletRequest request){
        return teamService.deleteTeam(id,request);
    }

}
