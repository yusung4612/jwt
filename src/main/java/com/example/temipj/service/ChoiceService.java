package com.example.temipj.service;

import com.example.temipj.domain.admin.Admin;
import com.example.temipj.domain.news.News;
import com.example.temipj.domain.news.Choice;
import com.example.temipj.dto.responseDto.ChoiceNewsResponseDto;
import com.example.temipj.dto.responseDto.ResponseDto;
import com.example.temipj.dto.responseDto.ChoiceResponseDto;
import com.example.temipj.dto.responseDto.NewsResponseDto;
import com.example.temipj.exception.CustomException;
import com.example.temipj.exception.ErrorCode;
import com.example.temipj.jwt.TokenProvider;
import com.example.temipj.repository.ChoiceNewsRepository;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
@Service
public class ChoiceService {

    private final TokenProvider tokenProvider;

    private final ChoiceNewsRepository choiceNewsRepository;

    private final NewsService newsService;

    //뉴스 선택 및 해제
    @Transactional
    public ResponseDto<?> choiceNews(Long newsId, HttpServletRequest request) {
        // 1. 토큰 유효성 확인
        if (!tokenProvider.validateToken(request.getHeader("Refresh_Token"))) {
            throw new CustomException(ErrorCode.INVALID_TOKEN);
        }
        // 2. tokenProvider Class의 SecurityContextHolder에 저장된 Admin 정보 확인
        Admin admin = tokenProvider.getAdminFromAuthentication();
        if (null == admin) {
            throw new CustomException(ErrorCode.ADMIN_NOT_FOUND);
        }
        // 3. 뉴스 유무 확인
        News news = newsService.isPresentNews(newsId);
        if (null == news) {
            throw new CustomException(ErrorCode.NOT_EXIST_EMPLOYEE);
        }

        // 4. 뉴스 선택 저장
        Choice findNewsSelect = choiceNewsRepository.findByNewsIdAndAdminId(news.getId(), admin.getId());
        if (null != findNewsSelect) {
            choiceNewsRepository.delete(findNewsSelect);
            return ResponseDto.success("뉴스 선택 해제");
        }
        Choice select = Choice.builder()
                .admin(admin)
                .news(news)
                .build();
        choiceNewsRepository.save(select);
        return ResponseDto.success("뉴스 선택");
    }

    // 선택한 뉴스 목록 조회
    @Transactional
    public ChoiceResponseDto<?> getChoiceAll(HttpServletRequest request) {
        // 1. 토큰 유효성 확인
        if (!tokenProvider.validateToken(request.getHeader("Refresh_Token"))) {
            throw new CustomException(ErrorCode.INVALID_TOKEN);
        }
        // 2. tokenProvider Class의 SecurityContextHolder에 저장된 Admin 정보 확인
        Admin admin = tokenProvider.getAdminFromAuthentication();
        if (null == admin) {
            throw new CustomException(ErrorCode.ADMIN_NOT_FOUND);
        }

        List<Choice> choiceList = choiceNewsRepository.findAllByAdmin(admin);
        List<NewsResponseDto> NewsResponseDtoList = new ArrayList<>();

        for (Choice selects : choiceList) {
            NewsResponseDtoList.add(
                    NewsResponseDto.builder()
                            .message(selects.getNews().getMessage())
                            .author(selects.getNews().getAuthor())
                            .build());
        }
        return ChoiceResponseDto.version(NewsResponseDtoList);
    }

    // 선택한 뉴스 중에서 검색
    @Transactional
    public ResponseDto<?> findNews(String keyword) {

//        List<News> choiceList = choiceRepository.findNews1(keyword);
        List<News> choiceList = choiceNewsRepository.findNews(keyword);
        // 검색된 항목 담아줄 리스트 생성
        List<ChoiceNewsResponseDto> ChoiceNewsResponseDtoList = new ArrayList<>();
        //for문을 통해서 List에 담아주기
        for (News news : choiceList) {
            ChoiceNewsResponseDtoList.add(
                    ChoiceNewsResponseDto.builder()
                            .message(news.getMessage())
                            .author(news.getAuthor())
                            .build());
        }
        return ResponseDto.success(ChoiceNewsResponseDtoList);
    }

}