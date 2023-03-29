package com.example.temipj.controller;

import com.example.temipj.dto.responseDto.NewsCrawlDto;
import com.example.temipj.service.NewsCrawlService;
import lombok.RequiredArgsConstructor;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class NewsCrawlController {

    private final NewsCrawlService newsCrawlService;

    @GetMapping("/crawl")
    public String newsCrawl(Model model) throws Exception{
        List<NewsCrawlDto> newsCrollList = newsCrawlService.getNewsDatas();
        model.addAttribute("newsCrawl", newsCrollList);

        return "newsCrawl";
    }

}
