package com.example.temipj.repository;

import com.example.temipj.domain.employee.Department;
import com.example.temipj.domain.employee.Division;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface DivisionRepository extends JpaRepository<Division, Long> {

    List<Division> findAllByOrderByCreatedAtDesc();

    Division findById(String divisionId);


}
