package com.example.temipj.repository;

import com.example.temipj.domain.member.Member;
import com.example.temipj.domain.news.News;
import com.example.temipj.domain.news.Selects;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface SelectsRepository extends JpaRepository<Selects, Long> {

    // 뉴스 선택
    Selects findByNewsIdAndMemberId(Long memberId, Long newsId);

    // 뉴스 검색
    List<Selects> findAllByMember(Member member);

    @Query(value = "SELECT p FROM News p WHERE p.message LIKE %:keyword% OR p.author LIKE %:keyword% ORDER BY p.createdAt desc")
    List <News> findNews1(@Param("keyword") String keyword);

}
