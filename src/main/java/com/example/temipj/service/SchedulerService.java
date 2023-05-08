package com.example.temipj.service;

import com.example.temipj.domain.news.News;
import com.example.temipj.repository.NewsRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

@Slf4j //로그
@RequiredArgsConstructor // final 멤버 변수를 자동으로 생성
@Component // 스프링이 필요 시 자동으로 생성하는 클래스 목록에 추가
public class SchedulerService {

    private final NewsRepository newsRepository;

    // 현재날짜 및 시간
    public String getNowDateTime24() {
        long time = System.currentTimeMillis();
        SimpleDateFormat dayTime = new SimpleDateFormat("yyyy.MM.dd kk:mm:ss E요일");
        String str = dayTime.format(new Date(time));

        return str;
    }

    // 초, 분, 시, 일, 월, 주 순서
    @Scheduled(cron = "0 0 9 31 12 *")
    public void selectNews() {
        System.out.println("[JopTime] : " + getNowDateTime24());
        System.out.println("뉴스 조회");

        // 전체조회
        List<News> newsList = newsRepository.findAll();

        for (int i = 0; i < newsList.size(); i++) {
//            if(newsList.get(i).getEnd_date().equals(LocalDate.now())) {
            if(newsList.get(i).getEnd_date().atStartOfDay().isBefore(LocalDateTime.now())) {
                System.out.println("뉴스 업데이트************************");
//                newsRepository.deleteById(newsList.get(i).getId());
                newsList.get(i).setChoiceNews("false"); // 상태 업데이트
                newsRepository.save(newsList.get(i)); // 업데이트된 상태를 저장
            }
        }
    }
}

