package com.example.temipj.domain;

import com.example.temipj.dto.requestDto.TeamRequestDto;
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
public class Team {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String title; //팀명

    @JoinColumn(name = "member_id", nullable = false)
    @ManyToOne(fetch = FetchType.LAZY)
    private Member member;

    @JsonIgnore
    @OneToMany(fetch = FetchType.LAZY)
    private List<Employee> employee;

    public void update(TeamRequestDto requestDto){
        this.title=requestDto.getTitle();
    }

    public boolean validateMember(Member member) {
        return !this.member.equals(member);
    }

}
