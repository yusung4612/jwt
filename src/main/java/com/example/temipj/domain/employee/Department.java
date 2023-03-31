package com.example.temipj.domain.employee;

import com.example.temipj.domain.Timestamped;
import com.example.temipj.dto.requestDto.DepartmentRequestDto;
import com.fasterxml.jackson.annotation.JsonIgnore;
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
public class Department extends Timestamped {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String department; // 하위부서 이름

    @JoinColumn
    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY)
    private Division division;

    @OneToMany(mappedBy = "department", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<Employee> employee;

    public void update(DepartmentRequestDto requestDto) {
        this.department = requestDto.getDepartment();
    }

}
