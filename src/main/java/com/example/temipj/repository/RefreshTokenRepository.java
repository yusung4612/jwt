package com.example.temipj.repository;


import com.example.temipj.domain.Member;
import com.example.temipj.domain.RefreshToken;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RefreshTokenRepository extends JpaRepository<RefreshToken, Long> {

    Optional<RefreshToken> findByMember(Member member);

    Optional<RefreshToken> findByValue(String value);

    Optional<RefreshToken> deleteByMemberId(Long id);
}