package com.example.temipj.domain;

import com.example.temipj.dto.requestDto.EmployeeRequestDto;
import com.fasterxml.jackson.annotation.JsonIgnore;
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
public class Employee extends Timestamped{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String empName; //직원이름

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

    @JsonIgnore
    @JoinColumn(nullable = false)
    @ManyToOne(fetch = FetchType.LAZY)
    private Member member;

    public void update(EmployeeRequestDto requestDto) {
        this.empName = requestDto.getEmpName();
        this.birth = requestDto.getBirth();
        this.extension_number = requestDto.getExtension_number();
        this.mobile_number = requestDto.getMobile_number();
        this.email = requestDto.getEmail();
        this.division = requestDto.getDivision();
        this.department = requestDto.getDepartment();
    }

    public boolean validateMember(Member member) {
        return !this.member.equals(member);
    }


}
