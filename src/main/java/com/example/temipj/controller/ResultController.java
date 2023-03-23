package com.example.temipj.controller;

import com.example.temipj.service.TestClass;
import lombok.RequiredArgsConstructor;
import org.json.JSONObject;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class ResultController {

    private final TestClass testClass;

    // 리더 선택
    @GetMapping("/result")
    private JSONObject selectLeader() {
        return testClass.getResult();
    }

}
