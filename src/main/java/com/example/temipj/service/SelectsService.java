package com.example.temipj.service;

import com.example.temipj.domain.member.Member;
import com.example.temipj.domain.news.News;
import com.example.temipj.domain.news.Selects;
import com.example.temipj.dto.responseDto.ResponseDto;
import com.example.temipj.dto.responseDto.SelectsResponseDto;
import com.example.temipj.dto.responseDto.NewsResponseDto;
import com.example.temipj.exception.CustomException;
import com.example.temipj.exception.ErrorCode;
import com.example.temipj.jwt.TokenProvider;
import com.example.temipj.repository.SelectsRepository;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
@Service
public class SelectsService {

    private final TokenProvider tokenProvider;

    private final SelectsRepository selectsRepository;

    private final NewsService newsService;

    //뉴스 선택 및 해제
    @Transactional
    public ResponseDto<?> selectsNews(Long newsId, HttpServletRequest request) {
        // 1. 토큰 유효성 확인
        if (!tokenProvider.validateToken(request.getHeader("Refresh_Token"))) {
            throw new CustomException(ErrorCode.INVALID_TOKEN);
        }
        // 2. tokenProvider Class의 SecurityContextHolder에 저장된 Member 정보 확인
        Member member = tokenProvider.getMemberFromAuthentication();
        if (null == member) {
            throw new CustomException(ErrorCode.MEMBER_NOT_FOUND);
        }
        // 3. 뉴스 유무 확인
        News news = newsService.isPresentNews(newsId);
        if (null == news) {
            throw new CustomException(ErrorCode.NOT_EXIST_EMPLOYEE);
        }

        // 4. 뉴스 선택 저장
        Selects findNewsSelect = selectsRepository.findByNewsIdAndMemberId(news.getId(), member.getId());
        if (null != findNewsSelect) {
            selectsRepository.delete(findNewsSelect);
            return ResponseDto.success("뉴스 선택 해제");
        }
        Selects select = Selects.builder()
                .member(member)
                .news(news)
                .build();
        selectsRepository.save(select);
        return ResponseDto.success("뉴스 선택");
    }

    // 선택한 뉴스 목록 조회
    @Transactional
    public SelectsResponseDto<?> getSelectsAll(HttpServletRequest request) {
        // 1. 토큰 유효성 확인
        if (!tokenProvider.validateToken(request.getHeader("Refresh_Token"))) {
            throw new CustomException(ErrorCode.INVALID_TOKEN);
        }
        // 2. tokenProvider Class의 SecurityContextHolder에 저장된 Member 정보 확인
        Member member = tokenProvider.getMemberFromAuthentication();
        if (null == member) {
            throw new CustomException(ErrorCode.MEMBER_NOT_FOUND);
        }

        List<Selects> selectsList = selectsRepository.findAllByMember(member);
        List<NewsResponseDto> NewsResponseDtoList = new ArrayList<>();

        for (Selects selects : selectsList) {
            NewsResponseDtoList.add(
                    NewsResponseDto.builder()
                            .message(selects.getNews().getMessage())
                            .author(selects.getNews().getAuthor())
                            .build());
        }
        return SelectsResponseDto.version(NewsResponseDtoList);
    }

    // 뉴스 검색
    @Transactional
    public ResponseDto<?> findNews(String keyword) {

        List<News> selectsList = selectsRepository.findNews1(keyword);
        // 검색된 항목 담아줄 리스트 생성
        List<NewsResponseDto> NewsResponseDtoList = new ArrayList<>();
        //for문을 통해서 List에 담아주기
        for (News news : selectsList) {
            NewsResponseDtoList.add(
                    NewsResponseDto.builder()
                            .message(news.getMessage())
                            .author(news.getAuthor())
                            .build());
        }
        return ResponseDto.success(NewsResponseDtoList);
    }

}
