package com.example.temipj.service;

import com.example.temipj.dto.responseDto.NewsCrollDto;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@RequiredArgsConstructor
@Service
public class NewsCrollService {

    private static String News_URL = "https://news.google.com/search?q=%EC%97%90%EB%B8%8C%EB%A6%AC%EB%B4%87&hl=ko&gl=KR&ceid=KR%3Ako";

    @PostConstruct
    public List<NewsCrollDto> getNewsDatas() throws IOException {
        List<NewsCrollDto> newsCrollList = new ArrayList<>();
        Document document = Jsoup.connect(News_URL).get();

        Elements contents = document.select("#yDmH0d c-wiz div div.FVeGwb.CVnAc.Haq2Hf.bWfURe div.ajwQHc.BL5WZb.RELBvb div main c-wiz div.lBwEZb.BL5WZb.GndZbb div div article");
        System.out.println(contents);
        for (Element content : contents) {
            NewsCrollDto newsCroll = NewsCrollDto.builder()
                    .title(content.select("h3 a").text())      // 제목
                    .url(content.select("h3 a").attr("href"))      // 링크
                    .author(content.select("div.wsLqz.RD0gLb img.tvs3Id.tvs3Id.lqNvvd.ICvKtf.WfKKme.IGhidc").attr("src"))// 신문사 이름 이미지
                    .build();
            newsCrollList.add(newsCroll);
        }
        System.out.println(Collections.unmodifiableList(newsCrollList));
        return newsCrollList;
    }

}
