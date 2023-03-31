package com.example.temipj.domain.news;

import com.example.temipj.domain.Timestamped;
import com.example.temipj.domain.admin.Admin;
import com.example.temipj.dto.requestDto.NewsRequestDto;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Builder
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class News extends Timestamped {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String message; //뉴스 제목 메세지

    @Column(nullable = false)
    private String author; // 작성자, 작성사이트

    @JoinColumn
    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY)
    private Admin admin;

    @Column(nullable = false)
    private String choiceNews = "false";

//    private LocalDateTime expirationDateTime; // expirationDateTime 속성 추가

    public void update(NewsRequestDto requestDto) {
        this.message = requestDto.getMessage();
        this.author = requestDto.getAuthor();
    }

    public boolean validateAdmin(Admin admin) {
        return !this.admin.equals(admin);
    }

    public void updateChoiceNews(Long id) {
        this.choiceNews = "true";
    }

    public void cancelChoiceNews(Long id) {
        this.choiceNews = "false";
    }
}