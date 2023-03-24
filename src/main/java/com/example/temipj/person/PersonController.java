package com.example.temipj.person;

import lombok.RequiredArgsConstructor;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/persons")
public class PersonController {

    @Autowired
    private PersonService personService;

    @GetMapping("/list")
    public String getAllPersons() throws JSONException {
        List<Person> persons = personService.getAllPersons();

        // JSON 배열 생성
        JSONArray personsArray = new JSONArray();

        for (Person person : persons) {
            // JSON 객체 생성
            JSONObject personObj = new JSONObject();
            personObj.put("name", person.getName());
            personObj.put("age", person.getAge());

            // 언어 정보를 담은 JSON 배열 생성
            JSONArray languagesArray = new JSONArray();
            String[] languages = person.getLanguage().split(",");
            for (String language : languages) {
                languagesArray.put(language.trim());
            }
            personObj.put("languages", languagesArray);

            // JSON 배열에 JSON 객체 추가
            personsArray.put(personObj);
        }
        return personsArray.toString();
    }

}
