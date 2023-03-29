package com.example.temipj.domain.news;

import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

@Getter
@Builder
@ToString

public class NewsCroll {

    private String title;
    private String url;
    private String author;

}
