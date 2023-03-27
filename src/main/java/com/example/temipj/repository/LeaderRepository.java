package com.example.temipj.repository;

import com.example.temipj.domain.employee.Employee;
import com.example.temipj.domain.employee.Leader;
import com.example.temipj.domain.admin.Admin;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface LeaderRepository extends JpaRepository<Leader, Long> {

    Optional<Leader> findById(Long id);

    List<Leader> findAllByAdmin(Admin admin);

    boolean existsByAdminAndEmployee(Admin admin, Employee employee);

    // 리더 선택
    Leader findByEmployeeIdAndAdminId(Long adminId, Long employeeId);

    //리더 검색
    @Query(value = "SELECT p FROM Employee p WHERE p.name LIKE %:keyword% OR p.birth LIKE %:keyword% " +
            "OR p.extension_number LIKE %:keyword% OR p.mobile_number LIKE %:keyword% " +
            "OR p.email LIKE %:keyword% ORDER BY p.createdAt desc")
//            "OR p.email LIKE %:keyword% OR p.department LIKE %:keyword% ORDER BY p.createdAt desc")
    List <Employee> searchLead(@Param("keyword") String keyword);
}
