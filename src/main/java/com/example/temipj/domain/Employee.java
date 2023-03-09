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
public class Employee {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name; //직원이름

    @Column(nullable = false)
    private String birth; //생일

    @Column(nullable = false)
    private String extension_number; //유선전화번호

    @Column(nullable = false, unique = true)
    private String mobile_number; //모바일번호

    @Column(nullable = false, unique = true)
    private String email; //이메일

    @Column(nullable = false)
    private String division; //팀 구분

    @Column(nullable = false)
    private String department; //부서

    @JoinColumn(nullable = false)
    @ManyToOne(fetch = FetchType.LAZY)
    private Member member;

}
