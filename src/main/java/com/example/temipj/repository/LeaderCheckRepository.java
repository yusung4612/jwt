package com.example.temipj.repository;

import com.example.temipj.domain.Employee;
import com.example.temipj.domain.LeaderCheck;
import com.example.temipj.domain.Member;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface LeaderCheckRepository extends JpaRepository<LeaderCheck, Long> {

    Optional<LeaderCheck> findById(Long id);

    List<LeaderCheck> findAllByOrderByModifiedAtDesc();

    Optional<LeaderCheck> findByMemberAndEmployee(Member member, Employee employee);
}
