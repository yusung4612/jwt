package com.example.temipj.domain.employee;

import com.example.temipj.domain.Timestamped;
import com.example.temipj.dto.requestDto.DivisionRequestDto;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Builder
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Division extends Timestamped {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id ;

    @Column(nullable = false)
    private String division; // 상위부서

    @OneToMany(mappedBy = "division", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<Department> department;

    // 상위부서 수정 메서드 생성
    public void update(DivisionRequestDto requestDto) {
        this.division = requestDto.getDivision();
    }
}
