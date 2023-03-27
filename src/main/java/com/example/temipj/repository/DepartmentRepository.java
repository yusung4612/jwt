package com.example.temipj.repository;

import com.example.temipj.domain.employee.Department;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface DepartmentRepository extends JpaRepository<Department, Long> {

    List<Department> findAllByOrderByCreatedAtDesc();

    Department findById(String departmentId);

}
