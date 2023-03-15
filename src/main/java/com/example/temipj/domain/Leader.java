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
public class Leader extends Timestamped{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

//    @Column(nullable = false)
//    private String division; //팀 구분
//
//    @Column(nullable = false)
//    private String department; //부서
//
//    @Column(nullable = false)
//    private String name; //직원이름
//
//    @Column(nullable = false)
//    private String mobile_number; //모바일 번호
//
//    @Column(nullable = false)
//    private String email;

    @JoinColumn(name = "member_id", nullable = false)
    @ManyToOne(fetch = FetchType.LAZY)
    private Member member;

    @JoinColumn(name = "employee_id", nullable = false)
    @ManyToOne(fetch = FetchType.LAZY)
    private Employee employee;

}
