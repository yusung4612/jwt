package com.example.temipj.repository;


import com.example.temipj.domain.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface MemberRepository extends JpaRepository<Member, Long> {

    Optional<Member> findById(Long id);

    Optional<Member> findByEmailId(String emailId);

//    Optional<Member> findByEmail(String email);

    Optional<Member> findByMembername(String membername);

//    Boolean existsByEmail(String email);
}
