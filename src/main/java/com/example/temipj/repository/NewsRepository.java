package com.example.temipj.repository;

import com.example.temipj.domain.news.News;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface NewsRepository extends JpaRepository<News, Long> {

    List<News>findAllByOrderByCreatedAtDesc();

    //뉴스 검색
    @Query(value = "SELECT p FROM News p WHERE p.message LIKE %:keyword% OR p.author LIKE %:keyword% " +
            " ORDER BY p.createdAt desc")
    List<News> searchArticle(@Param("keyword") String keyword);

    // 선택한 뉴스 조회
    @Query( value = "select p from News p where p.choiceNews = 'true'")
    List<News> findAllByChoiceNews();

    // 만료된 뉴스 삭제
//    List<News> findByExpirationDateTimeBefore(LocalDateTime currentTime);


//    News findByNewsIdAndAdminId(Long newsId, Long adminId);
//
//    List<News> findAllByAdmin(Admin admin);

    // 선택한 뉴스 중에서 검색
//    @Query(value = "SELECT p FROM News p WHERE p.choiceNews = 'true'")
//    List <Choice> findAllByChoiceNews(@Param("keyword") String keyword);

}
