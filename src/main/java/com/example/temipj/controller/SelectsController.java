package com.example.temipj.controller;

import com.example.temipj.dto.responseDto.ResponseDto;
import com.example.temipj.dto.responseDto.SelectsResponseDto;
import com.example.temipj.service.SelectsService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/selects")
public class SelectsController {

    private final SelectsService selectsService;

    // 노출될 뉴스 선택
    @PostMapping("/{newsId}")
    public ResponseDto<?> selectsNews(@PathVariable Long newsId, HttpServletRequest request) {
        return selectsService.selectsNews(newsId, request);
    }

    // 선택한 뉴스 목록 조회
    @GetMapping("/all")
    public SelectsResponseDto<?> getSelectsAll(HttpServletRequest request) {
        return selectsService.getSelectsAll(request);
    }

    // 뉴스 검색
    @GetMapping("/search")
    public ResponseDto<?> findNews(@RequestParam(value = "keyword") String keyword){
        return selectsService.findNews(keyword);
    }

}
