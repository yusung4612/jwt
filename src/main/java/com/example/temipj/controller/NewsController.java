package com.example.temipj.controller;

import com.example.temipj.domain.UserDetailsImpl;
import com.example.temipj.dto.requestDto.NewsRequestDto;
import com.example.temipj.dto.responseDto.ResponseDto;
import com.example.temipj.service.NewsService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/news")
public class NewsController {

    private final NewsService newsService;

    //뉴스 생성
    @PostMapping(value = "/create")
    private ResponseDto<?> createNews(@RequestBody NewsRequestDto newsRequestDto,
                                                    HttpServletRequest request) {
        return newsService.createNews(newsRequestDto, request);
    }

    //전체 뉴스 목록 조회
    @GetMapping(value = "/all")
    public ResponseDto<?> getNewsAll(@AuthenticationPrincipal UserDetailsImpl userDetails){
        return newsService.getNewsAll(userDetails);
    }

    //특정 뉴스 조회
    @GetMapping(value = "/{id}")
    public ResponseDto<?> getNews(@PathVariable Long id) {
        return newsService.getNews(id);
    }

    // 뉴스 수정
    @PutMapping(value = "/{id}")
    public ResponseDto<?> updateNews(@PathVariable Long id,
                                       @RequestBody NewsRequestDto newsRequestDto,
                                       HttpServletRequest request) {
        return newsService.updateNews(id, newsRequestDto, request);
    }

    // 뉴스 삭제
    @DeleteMapping("delete/{id}")
    public ResponseDto<?>deleteNews(@PathVariable Long id,HttpServletRequest request){
        return newsService.deleteNews(id,request);
    }

    // 뉴스 검색
    @GetMapping("/search")
    public ResponseDto<?> searchsNews(@RequestParam(value = "keyword") String keyword){
        return newsService.searchsNews(keyword);
    }
}
