package com.example.temipj.repository;

import com.example.temipj.domain.Employee;
import com.example.temipj.domain.Leader;
import com.example.temipj.domain.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

public interface LeaderRepository extends JpaRepository<Leader, Long> {

    Optional<Leader> findById(Long id);

    List<Leader> findAllByMember(Member member);

    boolean existsByMemberAndEmployee(Member member, Employee employee);

    //리더 체크
    Leader findByEmployeeIdAndMemberId(Long memberId, Long employeeId);

    //리더 검색
    @Query(value = "SELECT p FROM Employee p WHERE p.name LIKE %:keyword% OR p.birth LIKE %:keyword% " +
            "OR p.division LIKE %:keyword% OR p.extension_number LIKE %:keyword% OR p.mobile_number LIKE %:keyword% " +
            "OR p.email LIKE %:keyword% OR p.department LIKE %:keyword% ORDER BY p.createdAt desc")
    List <Employee> searchLead(@Param("keyword") String keyword);
}
