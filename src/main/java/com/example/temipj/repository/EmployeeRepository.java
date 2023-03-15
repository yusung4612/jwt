package com.example.temipj.repository;


import com.example.temipj.domain.Employee;
import com.example.temipj.domain.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
@Repository
public interface EmployeeRepository extends JpaRepository<Employee, Long> {
    Optional<Employee> findById(Long id);

    List<Employee> findAllByOrderByCreatedAtDesc();

    List<Employee> findAllByMember(Member member);

//    @Query("SELECT P FROM Employee p JOIN p.leader l WHERE l.member.id= :memberId")
//    List<Employee>findLeaderByEmployee();

}
