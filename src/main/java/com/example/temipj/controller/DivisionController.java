package com.example.temipj.controller;

import com.example.temipj.dto.requestDto.DepartmentRequestDto;
import com.example.temipj.dto.requestDto.DivisionRequestDto;
import com.example.temipj.dto.responseDto.ResponseDto;
import com.example.temipj.service.DivisionService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/divisions")
public class DivisionController {

    private final DivisionService divisionService;

    // 팀 생성
    @PostMapping(value = "/create")
    private ResponseDto<?> create(@RequestBody DivisionRequestDto divisionRequestDto,
                                  HttpServletRequest request) {
        return divisionService.createDivision(divisionRequestDto, request);
    }

    // 부서 전체 조회
    @GetMapping(value = "/all")
    public ResponseDto<?> getAll(){
        return divisionService.getDivisionAll();
    }

    // 부서 삭제
    @DeleteMapping("delete/{id}")
    public ResponseDto<?>delete(@PathVariable Long id,HttpServletRequest request){
        return divisionService.deleteDivision(id,request);
    }


}
