package com.example.temipj.repository;


import com.example.temipj.domain.Employee;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface EmployeeRepository extends JpaRepository<Employee, Long> {
    Optional<Employee> findById(Long id);

    Object findAllByOrderByModifiedAtDesc();
}
