package com.example.temipj.repository;

import com.example.temipj.domain.Leader;
import com.example.temipj.domain.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
@EnableJpaRepositories
public interface LeaderRepository extends JpaRepository<Leader, Long> {

    Optional<Leader> findById(Long id);

    List<Leader> findAllByMember(Member member);

    //리더 체크
    Leader findByEmployeeIdAndMemberId(Long memberId, Long employeeId);

}
