package com.example.temipj.repository;


import com.example.temipj.domain.employee.Employee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface EmployeeRepository extends JpaRepository<Employee, Long> {

    Optional<Employee> findById(Long id);

    List<Employee> findAllByOrderByCreatedAtDesc();

    // 직원 검색
    @Query(value = "SELECT p FROM Employee p WHERE p.name LIKE %:keyword% OR p.birth LIKE %:keyword% " +
            "OR p.division LIKE %:keyword% OR p.extension_number LIKE %:keyword% OR p.mobile_number LIKE %:keyword% " +
            "OR p.email LIKE %:keyword% OR p.department LIKE %:keyword% ORDER BY p.createdAt desc")
    List <Employee> searchEmp(@Param("keyword") String keyword);

    //테스트////////////////////
    @Query(value = "SELECT p FROM Employee p WHERE p.leader = 'true'")
    Employee findByLeader();
}
