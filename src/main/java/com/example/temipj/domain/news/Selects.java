package com.example.temipj.domain.news;

import com.example.temipj.domain.Timestamped;
import com.example.temipj.domain.member.Member;
import com.example.temipj.domain.news.News;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Builder
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Selects extends Timestamped {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @JoinColumn(name = "member_id", nullable = false)
    @ManyToOne(fetch = FetchType.LAZY)
    private Member member;

    @JoinColumn(name = "news_id", nullable = false)
    @ManyToOne(fetch = FetchType.LAZY)
    private News news;
}
