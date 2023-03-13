//package com.example.temipj.domain;
//
//import com.fasterxml.jackson.annotation.JsonIgnore;
//import jakarta.persistence.*;
//import lombok.AllArgsConstructor;
//import lombok.Builder;
//import lombok.Getter;
//import lombok.NoArgsConstructor;
//
//@Builder
//@Getter
//@NoArgsConstructor
//@AllArgsConstructor
//@Entity
//public class News extends Timestamped{
//
//    @Id
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
//    private Long id;
//
//    @Column(nullable = false)
//    private String messasge; //뉴스 내용
//
//    @Column(nullable = false)
//    private String author; //작성자
//
//    @Column(nullable = false)
//    private String begin_date; //표출 시작 시간
//
//    @Column(nullable = false)
//    private String end_date; //표출 만료 시간
//
//    @JsonIgnore
//    @JoinColumn(nullable = false)
//    @ManyToOne(fetch = FetchType.LAZY)
//    private Member member;
//
//}
