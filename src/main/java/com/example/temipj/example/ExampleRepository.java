package com.example.temipj.example;

import com.example.temipj.domain.employee.Employee;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ExampleRepository extends JpaRepository<Employee, Long> {
}
