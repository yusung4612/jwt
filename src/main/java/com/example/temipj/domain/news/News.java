package com.example.temipj.domain.news;

import com.example.temipj.domain.Timestamped;
import com.example.temipj.dto.requestDto.NewsRequestDto;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;

@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class News extends Timestamped {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String message; // 뉴스 제목 or 메세지

    @Column(nullable = false)
    private String author; // 작성자 or 출처

//    @JoinColumn
//    @JsonIgnore
//    @ManyToOne(fetch = FetchType.LAZY)
//    private Admin admin;

    @Column
    private String choiceNews = "false";

    @Column
    private LocalDate end_date; // 만료 일자

    public void update(NewsRequestDto requestDto) {
        this.message = requestDto.getMessage();
        this.author = requestDto.getAuthor();
    }

//    public boolean validateAdmin(Admin admin) {
//        return !this.admin.equals(admin);
//    }

    public void updateChoiceNews(Long id) {
        this.choiceNews = "true";
    }

    public void cancelChoiceNews(Long id) {
        this.choiceNews = "false";
    }
}