package com.example.temipj.dto.requestDto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class NewsRequestDto {

    private Long id;
    @NotBlank(message = "뉴스 메세지를 입력해주세요.")
    @Size(min = 1, max = 100, message = "최소 1자이상 최대 100자미만으로 입력해주세요.")
    @Pattern(regexp = "[a-zA-Zㄱ-ㅎ가-힣]*${1,100}", message = "메세지 형식을 확인해 주세요.")
    private String message; // 뉴스 메세지

    @NotBlank(message = "작성자를 입력해주세요.")
    @Size(min = 1, max = 20, message = "최소 1자이상 최대 20자미만으로 입력해주세요.")
    @Pattern(regexp = "[a-zA-Zㄱ-ㅎ가-힣]*${1,20}", message = "작성자명 형식을 확인해 주세요.")
    private String author; // 작성자

//    private LocalDate end_date;

}
