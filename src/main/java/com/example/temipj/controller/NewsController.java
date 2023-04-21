package com.example.temipj.controller;

import com.example.temipj.dto.requestDto.NewsRequestDto;
import com.example.temipj.dto.responseDto.ChoiceNewsResponseDto;
import com.example.temipj.dto.responseDto.ResponseDto;
import com.example.temipj.service.NewsService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/news")
public class NewsController {

    private final NewsService newsService;

    //뉴스 생성
    @PostMapping(value = "/create")
    @ResponseBody
    private ResponseDto<?> createNews(@RequestBody NewsRequestDto newsRequestDto, HttpServletRequest request) {
        return newsService.createNews(newsRequestDto, request);
    }

    //전체 뉴스 목록 조회
    @GetMapping(value = "/all")
    @ResponseBody
    public ResponseDto<?> getNewsAll(HttpServletRequest request) {
        return newsService.getNewsAll(request);
    }

    //특정 뉴스 조회
    @GetMapping(value = "/{id}")
    @ResponseBody
    public ResponseDto<?> getNews(@PathVariable Long id, HttpServletRequest request) {
        return newsService.getNews(id, request);
    }

    // 뉴스 수정
    @PutMapping(value = "/update/{id}")
    @ResponseBody
    public ResponseDto<?> updateNews(@PathVariable Long id, @RequestBody NewsRequestDto newsRequestDto, HttpServletRequest request) {
        return newsService.updateNews(id, newsRequestDto, request);
    }

    // 뉴스 삭제
    @DeleteMapping("delete/{id}")
    @ResponseBody
    public ResponseDto<?> deleteNews(@PathVariable Long id, HttpServletRequest request) {
        return newsService.deleteNews(id, request);
    }

    // 뉴스 검색
    @GetMapping("/search")
    @ResponseBody
    public ResponseDto<?> search(@RequestParam(value = "keyword") String keyword) {
        return newsService.searchNews(keyword);
    }

    // 노출시킬 뉴스 선택
    @PostMapping("/choice/{id}")
    @ResponseBody
    public ResponseDto<?> choiceNews(@PathVariable Long id, HttpServletRequest request) {
        return newsService.choiceNews(id, request);
    }

    // 선택한 뉴스 목록 조회
    @GetMapping("/choice/all")
    @ResponseBody
    public ChoiceNewsResponseDto<?> getChoiceAll(HttpServletRequest request) {
        return newsService.getChoiceAll(request);
    }

}