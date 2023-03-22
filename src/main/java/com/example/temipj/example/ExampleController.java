package com.example.temipj.example;

import lombok.RequiredArgsConstructor;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
@RequiredArgsConstructor
@RestController
public class ExampleController {
    private final ExampleService exampleService;

    @GetMapping("/data")
    public ResponseEntity<JSONArray> getData() {
        List<ExampleResponseDto> employeeList = exampleService.getData();

        JSONArray jsonArray = new JSONArray();

        for (ExampleResponseDto employee : employeeList) {
            JSONArray innerJsonArray = new JSONArray();

            JSONObject jsonObject = new JSONObject();
            jsonObject.put("email", employee.getEmail());
            jsonObject.put("name", employee.getName());
            jsonObject.put("birth", employee.getBirth());
            // 필요한 데이터 추가

            innerJsonArray.put(jsonObject);

            jsonArray.put(innerJsonArray);
        }

        return ResponseEntity.ok().body(jsonArray);
    }
}
