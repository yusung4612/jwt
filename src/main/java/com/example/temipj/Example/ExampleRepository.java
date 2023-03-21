package com.example.temipj.Example;

import com.example.temipj.domain.Employee;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ExampleRepository extends JpaRepository<Employee, Long> {
}
