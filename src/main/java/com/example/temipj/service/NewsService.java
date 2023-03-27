package com.example.temipj.service;

import com.example.temipj.domain.admin.Admin;
import com.example.temipj.domain.news.News;
import com.example.temipj.domain.UserDetailsImpl;
import com.example.temipj.dto.requestDto.NewsRequestDto;
import com.example.temipj.dto.responseDto.ResponseDto;
import com.example.temipj.dto.responseDto.NewsResponseDto;
import com.example.temipj.exception.CustomException;
import com.example.temipj.exception.ErrorCode;
import com.example.temipj.jwt.TokenProvider;
import com.example.temipj.repository.NewsRepository;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class NewsService {

    private final NewsRepository newsRepository;

    private final TokenProvider tokenProvider;

    // 뉴스 등록
    @Transactional
    public ResponseDto<?> createNews(NewsRequestDto requestDto, HttpServletRequest request) {
        // 1. 토큰 유효성 확인
        if (!tokenProvider.validateToken(request.getHeader("Refresh_Token"))) {
//            return ResponseDto.fail(ErrorCode.INVALID_TOKEN.name(), ErrorCode.INVALID_TOKEN.getMessage());
            throw new CustomException(ErrorCode.INVALID_TOKEN);
        }
        // 2. tokenProvider Class의 SecurityContextHolder에 저장된 Member 정보 확인
        Admin admin = (Admin) tokenProvider.getAdminFromAuthentication();
        if (null == admin) {
//            return ResponseDto.fail(ErrorCode.MEMBER_NOT_FOUND.name(), ErrorCode.MEMBER_NOT_FOUND.getMessage());
            throw new CustomException(ErrorCode.ADMIN_NOT_FOUND);
        }
        // 3. 뉴스 등록
        if (requestDto.getMessage().isEmpty())
//            return ResponseDto.fail(ErrorCode.NOT_BLANK_NAME.name(), ErrorCode.NOT_BLANK_NAME.getMessage());
            throw new CustomException(ErrorCode.NOT_BLANK_NAME);

        News news = News.builder()
                .message(requestDto.getMessage())
                .author(requestDto.getAuthor())
                .admin(admin)
                .build();
        newsRepository.save(news);

        return ResponseDto.success(
                NewsResponseDto.builder()
                        .message(requestDto.getMessage())
                        .author(requestDto.getAuthor())
                        .build());
    }

    // 전체 뉴스 조회
    @Transactional
    public ResponseDto<?> getNewsAll(UserDetailsImpl userDetails){

        List<News> newsList = newsRepository.findAllByOrderByCreatedAtDesc();
        List<NewsResponseDto> NewsResponseDtoList = new ArrayList<>();

        for (News news : newsList) {
            NewsResponseDtoList.add(
                    NewsResponseDto.builder()
                            .message(news.getMessage())
                            .author(news.getAuthor())
                            .build());
        }
        return ResponseDto.success(NewsResponseDtoList);
    }

    // 특정 뉴스 조회
    @Transactional
    public ResponseDto<?> getNews(Long id) {
        //뉴스 유무 확인
        News news = isPresentNews(id);
        if (null == news) {
//            return ResponseDto.fail(ErrorCode.NOT_EXIST_EMPLOYEE.name(),ErrorCode.NOT_EXIST_EMPLOYEE.getMessage());
            throw new CustomException(ErrorCode.NOT_EXIST_EMPLOYEE);
        }
        return ResponseDto.success(news);
    }

    // 뉴스 수정
    @Transactional
//    public ResponseDto<?> updateEmp(Long id, EmployeeRequestDto requestDto, HttpServletRequest request) {
    public ResponseDto<?> updateNews(Long id, NewsRequestDto requestDto, HttpServletRequest request) {
        // 1. 토큰 유효성 확인
        if (!tokenProvider.validateToken(request.getHeader("Refresh_Token"))) {
//            return ResponseDto.fail(ErrorCode.INVALID_TOKEN.name(), ErrorCode.INVALID_TOKEN.getMessage());
            throw new CustomException(ErrorCode.INVALID_TOKEN);
        }
        // 2. 뉴스 유무 확인
        News news = isPresentNews(id);
        if (null == news) {
//            return ResponseDto.fail(ErrorCode.NOT_EXIST_EMPLOYEE.name(), ErrorCode.NOT_EXIST_EMPLOYEE.getMessage());
            throw new CustomException(ErrorCode.NOT_EXIST_EMPLOYEE);
        }
        // 3. tokenProvider Class의 SecurityContextHolder에 저장된 Member 정보 확인
        Admin admin = (Admin) tokenProvider.getAdminFromAuthentication();
        if (news.validateAdmin(admin)) {
//            return ResponseDto.fail(ErrorCode.EMPLOYEE_UPDATE_WRONG_ACCESS.name(), ErrorCode.EMPLOYEE_UPDATE_WRONG_ACCESS.getMessage());
            throw new CustomException(ErrorCode.EMPLOYEE_UPDATE_WRONG_ACCESS);
        }
        // 4. 뉴스 수정
        news.update(requestDto);
//        return ResponseDto.version(employee);
        return ResponseDto.success(news);
    }

    // 뉴스 삭제
    @Transactional
    //    public ResponseDto<?> deleteEmp(Long id, HttpServletRequest request) {
    public ResponseDto<?> deleteNews(Long id, HttpServletRequest request) {
        // 1. 토큰 유효성 확인
        if (!tokenProvider.validateToken(request.getHeader("Refresh_Token"))) {
//            return ResponseDto.fail(ErrorCode.INVALID_TOKEN.name(), ErrorCode.INVALID_TOKEN.getMessage());
            throw new CustomException(ErrorCode.INVALID_TOKEN);
        }
        // 2. 뉴스 유무 확인
        News news = isPresentNews(id);
        if (null == news) {
//            return ResponseDto.fail(ErrorCode.NOT_EXIST_EMPLOYEE.name(), ErrorCode.NOT_EXIST_EMPLOYEE.getMessage());
            throw new CustomException(ErrorCode.NOT_EXIST_EMPLOYEE);
        }
        // 3. tokenProvider Class의 SecurityContextHolder에 저장된 Admin 정보 확인
        Admin admin = (Admin) tokenProvider.getAdminFromAuthentication();
        if (news.validateAdmin(admin)) {
//            return ResponseDto.fail(ErrorCode.EMPLOYEE_UPDATE_WRONG_ACCESS.name(), ErrorCode.EMPLOYEE_UPDATE_WRONG_ACCESS.getMessage());
            throw new CustomException(ErrorCode.EMPLOYEE_UPDATE_WRONG_ACCESS);
        }
        // 4. 뉴스 삭제
        newsRepository.delete(news);
//        return ResponseDto.version("해당 뉴스가 삭제되었습니다.");
        return ResponseDto.success("해당 뉴스가 삭제되었습니다.");
    }

    // 뉴스 검색
    @Transactional
    public ResponseDto<?> searchsNews(String keyword) {
        List<News> newsList = newsRepository.searchNews1(keyword);
        // 검색된 항목 담아줄 리스트 생성
        List<NewsResponseDto> newsResponseDtoList = new ArrayList<>();
        // for문을 통해서 List에 담아주기
        for (News news : newsList) {
            newsResponseDtoList.add(
                    NewsResponseDto.builder()
                            .message(news.getMessage())
                            .author(news.getAuthor())
                            .build()
            );
        }
        return ResponseDto.success(newsResponseDtoList);
    }



    // 뉴스 유무 확인 메서드 생성
    @Transactional
    public News isPresentNews(Long id) {
        Optional<News> optionalNews = newsRepository.findById(id);
        return optionalNews.orElse(null);
    }

//    @Transactional
//    public Member validateMember(HttpServletRequest request) {
//        if (!tokenProvider.validateToken(request.getHeader("Refresh-Token"))) {
//            return null;
//        }
//        return tokenProvider.getMemberFromAuthentication();
//    }
//

}
