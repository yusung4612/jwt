package com.example.temipj.domain.employee;

import com.example.temipj.domain.Timestamped;
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
public class Employee extends Timestamped {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id ;

    @Column(nullable = false)
    private String name; // 직원이름

    @Column(nullable = false)
    private String birth; // 생일
    @Column(nullable = false, unique = true)
    private String extension_number; // 유선전화번호

    @Column(nullable = false, unique = true)
    private String mobile_number; // 모바일번호

    @Column(nullable = false, unique = true)
    private String email; // 이메일

    @JoinColumn
    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY)
    private Department department;

    @Column(nullable = false)
    private String leader = "false";

    public void update(EmployeeRequestDto requestDto) {
        this.name = requestDto.getName();
        this.birth = requestDto.getBirth();
        this.extension_number = requestDto.getExtension_number();
        this.mobile_number = requestDto.getMobile_number();
        this.email = requestDto.getEmail();
    }

    public void updateLeader(Long id) {
        this.leader = "true";
    }

    public void cancelLeader(Long id) {
        this.leader = "false";
    }
}
