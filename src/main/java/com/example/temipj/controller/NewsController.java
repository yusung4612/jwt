package com.example.temipj.controller;

import com.example.temipj.domain.news.News;
import com.example.temipj.dto.requestDto.NewsRequestDto;
import com.example.temipj.dto.responseDto.ChoiceNewsResponseDto;
import com.example.temipj.dto.responseDto.ResponseDto;
import com.example.temipj.repository.NewsRepository;
import com.example.temipj.service.NewsService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/news")
public class NewsController {

    private final NewsService newsService;

    private final NewsRepository newsRepository;

    //뉴스 생성
    @PostMapping(value = "/create")
    private ResponseDto<?> createNews(@RequestBody NewsRequestDto newsRequestDto,
                                      HttpServletRequest request) {
        return newsService.createNews(newsRequestDto, request);
    }

    //전체 뉴스 목록 조회
    @GetMapping(value = "/all")
    public ResponseDto<?> getNewsAll(){
        return newsService.getNewsAll();
    }

    //특정 뉴스 조회
    @GetMapping(value = "/{id}")
    public ResponseDto<?> getNews(@PathVariable Long id) {
        return newsService.getNews(id);
    }

    // 뉴스 수정
    @PutMapping(value = "/update/{id}")
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
    public ResponseDto<?> search(@RequestParam(value = "keyword") String keyword){
        return newsService.searchNews(keyword);
    }

    // 노출시킬 뉴스 선택
    @PostMapping("/choice/{id}")
    public ResponseDto<?> choiceNews(@PathVariable Long id, HttpServletRequest request) {
        return newsService.choiceNews(id, request);
    }

    // 선택한 뉴스 목록 조회
    @GetMapping("/choice/all")
    public ChoiceNewsResponseDto<?> getChoiceAll() {
        return newsService.getChoiceAll();
    }

    // 선택한 뉴스 목록중에서 검색
//    @GetMapping("/search")
//    public ResponseDto<?> findNews(@RequestParam(value = "keyword") String keyword){
//        return newsService.findChoiceNews(keyword);
//    }
}