package com.example.temipj.repository;

import com.example.temipj.domain.admin.Admin;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface AdminRepository extends JpaRepository<Admin, Long> {

    Optional<Admin> findById(Long id);

    Optional<Admin> findByEmailId(String emailId);

//    Optional<Member> findByEmail(String email);

    Optional<Admin> findByAdminName(String adminName);

//    Boolean existsByEmail(String email);
}
