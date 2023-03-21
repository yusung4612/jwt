package com.example.temipj.controller;

import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@ResponseBody
//@RequiredArgsConstructor
public class MainController {

    @GetMapping("/")
    public String main(Model model){
        return "static/index.html"; //메인페이지로 연결
    }
}