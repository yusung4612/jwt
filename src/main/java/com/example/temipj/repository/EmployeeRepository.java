package com.example.temipj.repository;


import com.example.temipj.domain.employee.Employee;
import com.example.temipj.dto.responseDto.TestDto.MapperDto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface EmployeeRepository extends JpaRepository<Employee, Long> {

    Optional<Employee> findById(Long id);

    List<Employee> findAllByOrderByCreatedAtDesc();

//    List<Employee> findAllLeader();

    // 직원 검색
    @Query(value = "SELECT p FROM Employee p WHERE p.name LIKE %:keyword% OR p.birth LIKE %:keyword% " +
            "OR p.extension_number LIKE %:keyword% OR p.mobile_number LIKE %:keyword% " +
            "OR p.email LIKE %:keyword% ORDER BY p.createdAt desc")
    List <Employee> searchEmp(@Param("keyword") String keyword);

//    @Query(value = "SELECT p FROM Employee p WHERE p.leader = 'true'")
//    List<Employee> findAllLeader();

    //    List<Employee> findByDivisionAndLeader(String division, String leader);
    Employee findByDepartmentAndLeader(String department, String leader);

    @Query(value = "SELECT a FROM Employee a LEFT JOIN Leader b ON a.id = b.employee.id WHERE a.leader = 'true'")
    List<Employee> findAllLeader();

    @Query( nativeQuery = true,
            value = "select a.*  \n" +
                    "      from Employee a \n" +
                    "inner join department b on a.department_id = b.id \n" +
                    "inner join division c on b.division_id = c.id \n" +
                    "      where a.leader = 'true' \n" +
                    "        and c.division = :paramDivision ")
    List<Employee> test(@Param("paramDivision") String paramDivision);
}
