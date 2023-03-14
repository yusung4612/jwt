package com.example.temipj.domain;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Builder
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class LeaderCheck extends Timestamped{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

//    @Column(nullable = false)
//    private String division; //팀 구분
//
//    @Column(nullable = false)
//    private String department; //부서
//
//
//    @Column(nullable = false)
//    private String name; //직원이름
//
//    @Column(nullable = false)
//    private String telephone; //유선전화번호
//
//    @Column(nullable = false)
//    private String email; //유선전화번호

//    @JsonIgnore
    @JoinColumn(nullable = false)
    @ManyToOne(fetch = FetchType.LAZY)
    private Member member;

    @JoinColumn(nullable = false)
    @ManyToOne(fetch = FetchType.LAZY)
    private Employee employee;

}
