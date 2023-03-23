package com.example.temipj.domain.employee;

import com.example.temipj.domain.member.Member;
import com.example.temipj.domain.Timestamped;
import com.example.temipj.dto.requestDto.EmployeeRequestDto;
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
public class Employee extends Timestamped {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id ;

    @Column(nullable = false)
    private String name; //직원이름

    @Column(nullable = false)
    private String birth; //생일

    @Column(nullable = false)
    private String division; //팀 구분

    @Column(nullable = false, unique = true)
    private String extension_number; //유선전화번호

    @Column(nullable = false, unique = true)
    private String mobile_number; //모바일번호

    @Column(nullable = false, unique = true)
    private String email; //이메일

    @Column(nullable = false)
    private String department; //부서

    @JoinColumn(nullable = false)
    @ManyToOne(fetch = FetchType.LAZY)
    private Member member;

    //테스트//////////////////////////////////
    @Column
    private String leader = "false";
    /////////////////////////////////////////

//    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
//    private List<Leader> leaderCheck;

    public void update(EmployeeRequestDto requestDto) {
        this.name = requestDto.getName();
        this.birth = requestDto.getBirth();
        this.division = requestDto.getDivision();
        this.extension_number = requestDto.getExtension_number();
        this.mobile_number = requestDto.getMobile_number();
        this.email = requestDto.getEmail();
        this.department = requestDto.getDepartment();
    }

    public boolean validateMember(Member member) {
        return !this.member.equals(member);
    }

    public void updateLeader(Long Id) {
        this.leader = "true";
    }

}
