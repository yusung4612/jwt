package com.example.temipj.controller;

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

    // 상위부서 생성
    @PostMapping(value = "/create")
    @ResponseBody
    private ResponseDto<?> create(@RequestBody DivisionRequestDto divisionRequestDto,
                                  HttpServletRequest request) {
        return divisionService.createDivision(divisionRequestDto, request);
    }

    // 상위부서 전체 조회
    @GetMapping(value = "/all")
    @ResponseBody
    public ResponseDto<?> getAll(HttpServletRequest request){
        return divisionService.getDivisionAll(request);
    }

    // 특정 상위부서 조회
    @GetMapping(value = "/{id}")
    @ResponseBody
    public ResponseDto<?> get(@PathVariable Long id, HttpServletRequest request) {
        return divisionService.getDivision(id, request);
    }

    // 상위부서 수정
    @PutMapping(value = "/update/{id}")
    public ResponseDto<?> update(@PathVariable Long id,
                                 @RequestBody DivisionRequestDto divisionRequestDto,
                                 HttpServletRequest request) {
        return divisionService.updateDivision(id, divisionRequestDto, request);
    }

    // 상위부서 삭제
    @DeleteMapping("delete/{id}")
    public ResponseDto<?>delete(@PathVariable Long id,HttpServletRequest request){
        return divisionService.deleteDivision(id,request);
    }

}
