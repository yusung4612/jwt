package com.example.temipj.controller;

import com.example.temipj.dto.responseDto.NewsCrollDto;
import com.example.temipj.service.NewsCrollService;
import lombok.RequiredArgsConstructor;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class NewsCrollController {

    private final NewsCrollService newsCrollService;

    @GetMapping("/croll")
    public String news(Model model) throws Exception{
        List<NewsCrollDto> newsCrollList = newsCrollService.getNewsDatas();
        model.addAttribute("newsCroll", newsCrollList);

        return "newsCroll";
    }

}
