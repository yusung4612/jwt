package com.example.temipj.repository;


import com.example.temipj.domain.admin.Admin;
import com.example.temipj.domain.RefreshToken;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RefreshTokenRepository extends JpaRepository<RefreshToken, Long> {

    Optional<RefreshToken> findByAdmin(Admin admin);

    Optional<RefreshToken> findByValue(String value);

    Optional<RefreshToken> deleteByAdminId(Long id);
}