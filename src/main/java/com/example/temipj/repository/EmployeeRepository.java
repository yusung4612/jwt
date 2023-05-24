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
            "OR p.extension_number LIKE %:keyword% OR p.mobile_number LIKE %:keyword% OR p.department.department LIKE %:keyword% " +
            "OR p.email LIKE %:keyword% OR p.department.division.division LIKE %:keyword% ORDER BY p.createdAt desc")
    List <Employee> searchEmp(@Param("keyword") String keyword);

//    @Query(value = "SELECT p FROM Employee p WHERE p.name LIKE %:keyword% OR p.birth LIKE %:keyword% " +
//            "OR p.extension_number LIKE %:keyword% OR p.mobile_number LIKE %:keyword% " +
//            "OR p.email LIKE %:keyword% ORDER BY p.createdAt desc")
//    List <Employee> searchEmp(@Param("keyword") String keyword);

//    @Query(value = "SELECT p FROM Employee p WHERE p.leader = 'true'")
//    List<Employee> findAllLeader();

    //    List<Employee> findByDivisionAndLeader(String division, String leader);
//    Employee findByDepartmentAndLeader(String department, String leader);

//    @Query(value = "SELECT a FROM Employee a LEFT JOIN Leader b ON a.id = b.employee.id WHERE a.leader = 'true'")
//    List<Employee> findAllLeader();

    // 전체 리더 조회
    @Query(value = "SELECT a,b,c " +
            "FROM Employee a " +
            "INNER JOIN Department b " +
            "ON a.department.id = b.id " +
            "LEFT JOIN Division c ON c.id = b.division.id " +
            "where a.leader = 'true'")
    List<Employee> getAllLeaders();

//    void delete(Optional<Employee> employee1);


    //============================리더테스트============================
    // 리더 조회
    @Query( value = "select p from Employee p where p.leader = 'true'")
    List<Employee> findAllByLeader();

    Employee findTop1ByOrderByModifiedAtDesc();

}
