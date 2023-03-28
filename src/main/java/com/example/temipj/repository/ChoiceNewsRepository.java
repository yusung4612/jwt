package com.example.temipj.repository;

import com.example.temipj.domain.admin.Admin;
import com.example.temipj.domain.news.News;
import com.example.temipj.domain.news.Choice;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ChoiceNewsRepository extends JpaRepository<Choice, Long> {

    // 뉴스 선택
    Choice findByNewsIdAndAdminId(Long newsId, Long adminId);


    List<Choice> findAllByAdmin(Admin admin);

    // 선택한 뉴스 목록 중에서 검색
    @Query(value = "SELECT p FROM News p WHERE p.message LIKE %:keyword% OR p.author LIKE %:keyword% ORDER BY p.createdAt desc")
    List <News> findNews(@Param("keyword") String keyword);

}
