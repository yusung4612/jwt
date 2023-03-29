package com.example.temipj.controller;

import com.example.temipj.dto.responseDto.ResponseDto;
import com.example.temipj.dto.responseDto.ChoiceResponseDto;
import com.example.temipj.service.ChoiceService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/choice")
public class ChoiceController {

    private final ChoiceService choiceService;

    // 노출시킬 뉴스 선택
    @PostMapping("/{newsId}")
    public ResponseDto<?> choiceNews(@PathVariable Long newsId, HttpServletRequest request) {
        return choiceService.choiceNews(newsId, request);
    }

    // 선택한 뉴스 목록 조회
    @GetMapping("/all")
    public ChoiceResponseDto<?> getChoiceAll(HttpServletRequest request) {
        return choiceService.getChoiceAll(request);
    }

    // 선택한 뉴스 목록중에서 검색
    @GetMapping("/search")
    public ResponseDto<?> findNews(@RequestParam(value = "keyword") String keyword){
        return choiceService.findNews(keyword);
    }

}