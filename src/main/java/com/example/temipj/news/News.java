//package com.example.temipj.news;
//
//import com.example.temipj.domain.Member;
//import com.fasterxml.jackson.annotation.JsonIgnore;
//import jakarta.persistence.*;
//import lombok.*;
//
//@Builder
//@Getter
//@Setter
//@ToString
//@NoArgsConstructor
//@AllArgsConstructor
//@Entity
//public class News {
//
//    @Id
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
//    private Long id;
//
//    private String title; //뉴스 제목
//
//    private String author; //작성자
//
//    private String URL;
//
////    @Column(nullable = false)
////    private String begin_date; //표출 시작 시간
////
////    @Column(nullable = false)
////    private String end_date; //표출 만료 시간
//
//    @JsonIgnore
//    @JoinColumn(nullable = false)
//    @ManyToOne(fetch = FetchType.LAZY)
//    private Member member;
//
//}
